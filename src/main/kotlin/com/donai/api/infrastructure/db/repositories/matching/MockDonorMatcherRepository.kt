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
                distanceMeters = 1200.0
            ),
            MatchingDonor(
                donorId = "donor-2",
                distanceMeters = 3500.0
            ),
            MatchingDonor(
                donorId = "donor-3",
                distanceMeters = 7200.0
            )
        ).filter {
            it.distanceMeters <= criteria.radiusMeters
        }
    }
}