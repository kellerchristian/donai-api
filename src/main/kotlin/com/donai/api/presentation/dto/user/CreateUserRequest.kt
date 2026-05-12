package com.donai.api.presentation.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val firebaseUid: String,
    val name: String,
    val email: String,
    val bloodGroup: String,
    val rhFactor: String,
    val latitude: Double,
    val longitude: Double,
    val locationDisplay: String,
    val gdprAccepted: Boolean
)