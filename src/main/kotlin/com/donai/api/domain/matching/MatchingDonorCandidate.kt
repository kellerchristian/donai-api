package com.donai.api.domain.matching

import com.donai.api.domain.user.User

data class MatchingDonorCandidate(
    val user: User,
    val distanceMeters: Double
)