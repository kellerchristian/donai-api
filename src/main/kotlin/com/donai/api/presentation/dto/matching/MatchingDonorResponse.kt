package com.donai.api.presentation.dto.matching

import com.donai.api.domain.matching.MatchingDonor
import kotlinx.serialization.Serializable

@Serializable
data class MatchingDonorResponse(
    val donorId: String,
    val distanceMeters: Double,
    val score: Double
)

fun MatchingDonor.toResponse() =
    MatchingDonorResponse(
        donorId = donorId,
        distanceMeters = distanceMeters,
        score = score
    )