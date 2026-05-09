package com.donai.api.application.matching

import com.donai.api.domain.matching.DonorMatcherRepository
import com.donai.api.domain.matching.MatchingCriteria
import com.donai.api.domain.matching.MatchingDonor
import com.donai.api.domain.request.RequestRepository

class FindMatchingDonorsUseCase(
    private val requestRepository: RequestRepository,
    private val donorMatcherRepository: DonorMatcherRepository
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

        val criteria = MatchingCriteria(
            bloodType = request.bloodType,
            location = request.location,
            radiusMeters = effectiveRadius
        )

        return donorMatcherRepository.findMatchingDonors(criteria)
            .sortedBy { it.distanceMeters }
    }
}