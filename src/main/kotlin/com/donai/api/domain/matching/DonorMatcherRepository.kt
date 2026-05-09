package com.donai.api.domain.matching

interface DonorMatcherRepository {

    fun findMatchingDonors(
        criteria: MatchingCriteria
    ): List<MatchingDonor>
}