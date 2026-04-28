package com.donai.api.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestRequest(
    val bloodGroup: String,
    val rhFactor: String,
    val quantity: Int,
    val lat: Double,
    val lng: Double,
    val description: String
)