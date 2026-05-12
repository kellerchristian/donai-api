package com.donai.api.application.matching

import com.donai.api.domain.matching.DonorMatchingEngine
import com.donai.api.domain.matching.DonorRepository
import com.donai.api.domain.matching.MatchingDonor
import com.donai.api.domain.request.RequestRepository

class FindMatchingDonorsUseCase(
    private val requestRepository: RequestRepository,
    private val donorRepository: DonorRepository,
    private val donorMatchingEngine: DonorMatchingEngine
) {

    companion object {
        private const val DEFAULT_RADIUS_METERS = 50_000.0
    }

    operator fun invoke(
        requestId: String,
        radiusMeters: Double?
    ): List<MatchingDonor> {

        val request = requestRepository.getById(requestId)
            ?: throw IllegalArgumentException("Request not found")

        val effectiveRadius = radiusMeters
            ?.takeIf { it > 0 }
            ?: DEFAULT_RADIUS_METERS

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