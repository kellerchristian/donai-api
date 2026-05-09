package com.donai.api.domain.matching

data class MatchingDonor(
    val donorId: String,
    val distanceMeters: Double,
    val score: Double
)