package com.donai.api.presentation.dto.event

import com.donai.api.domain.event.DonationEvent
import kotlinx.serialization.Serializable

@Serializable
data class DonationEventResponse(
    val id: String,

    val donorId: String,

    val requestId: String,

    val commitmentId: String,

    val donatedAt: String,

    val createdAt: String
)

fun DonationEvent.toResponse() = DonationEventResponse(
    id = id,

    donorId = donorId,

    requestId = requestId,

    commitmentId = commitmentId,

    donatedAt = donatedAt.toString(),

    createdAt = createdAt.toString()
)