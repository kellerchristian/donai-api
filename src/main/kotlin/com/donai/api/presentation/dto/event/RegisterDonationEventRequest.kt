package com.donai.api.presentation.dto.event

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDonationEventRequest(
    val donorId: String,
    val requestId: String,
    val commitmentId: String
)