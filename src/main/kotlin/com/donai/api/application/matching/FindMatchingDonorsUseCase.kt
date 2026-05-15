package com.donai.api.application.matching

import com.donai.api.config.MatchingConfig
import com.donai.api.domain.matching.DonorMatchingEngine
import com.donai.api.domain.matching.DonorRepository
import com.donai.api.domain.matching.MatchingDonor
import com.donai.api.domain.request.RequestRepository

class FindMatchingDonorsUseCase(
    private val requestRepository: RequestRepository,
    private val donorRepository: DonorRepository,
    private val donorMatchingEngine: DonorMatchingEngine
) {
    operator fun invoke(
        requestId: String,
        radiusMeters: Double? = null
    ): List<MatchingDonor> {

        val request = requestRepository.getById(requestId)
            ?: throw IllegalArgumentException("Request not found")

        val effectiveRadius = radiusMeters
            ?.takeIf { it > 0 }
            ?: MatchingConfig.DEFAULT_RADIUS_METERS

        val candidates = donorRepository.findCandidatesNear(
            location = request.location,
            radiusMeters = effectiveRadius
        )

        return donorMatchingEngine.match(
            request = request,
            candidates = candidates
        )
    }
}