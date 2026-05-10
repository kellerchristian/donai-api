package com.donai.api.infrastructure.db.repositories.matching



import com.donai.api.domain.matching.DonorMatcherRepository
import com.donai.api.domain.matching.MatchingCriteria
import com.donai.api.domain.matching.MatchingDonor
import kotlin.random.Random

class MockDonorMatcherRepository : DonorMatcherRepository {

    override fun findMatchingDonors(
        criteria: MatchingCriteria
    ): List<MatchingDonor> {

        return listOf(
            MatchingDonor(
                donorId = "donor-1",
                distanceMeters = 1200.0,
                score = 0.95
            ),
            MatchingDonor(
                donorId = "donor-2",
                distanceMeters = 3500.0,
                score = 0.87
            ),
            MatchingDonor(
                donorId = "donor-3",
                distanceMeters = 7200.0,
                score = 0.81
            )
        ).filter {
            it.distanceMeters <= criteria.radiusMeters
        }
    }
}