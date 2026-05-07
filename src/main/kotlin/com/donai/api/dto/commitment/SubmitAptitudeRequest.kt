package com.donai.api.dto.commitment

import kotlinx.serialization.Serializable

@Serializable
data class SubmitAptitudeRequest(
    val responses: String
)