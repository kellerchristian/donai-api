package com.donai.api.domain.matching

import com.donai.api.domain.shared.GeoLocation

interface DonorRepository {

    fun findCandidatesNear(
        location: GeoLocation,
        radiusMeters: Double
    ): List<MatchingDonorCandidate>
}