package com.donai.api.presentation.dto.user

import com.donai.api.domain.user.User
import kotlinx.serialization.Serializable
import java.time.Instant
import kotlinx.serialization.Contextual

@Serializable
data class UserResponse(
    val id: String,
    val firebaseUid: String,
    val name: String,
    val email: String,

    val bloodGroup: String,
    val rhFactor: String,

    val latitude: Double,
    val longitude: Double,
    val locationDisplay: String,

    val availableToDonate: Boolean,

    @Contextual
    val lastDonationAt: Instant?,

    @Contextual
    val nextEligibleAt: Instant?,

    val gdprAccepted: Boolean,

    @Contextual
    val gdprAcceptedAt: Instant?,

    @Contextual
    val createdAt: Instant,

    @Contextual
    val updatedAt: Instant
)

fun User.toResponse() = UserResponse(
    id = id,
    firebaseUid = firebaseUid,
    name = name,
    email = email,

    bloodGroup = bloodType.bloodGroup.name,
    rhFactor = bloodType.rhFactor.name,

    latitude = location.latitude,
    longitude = location.longitude,
    locationDisplay = locationDisplay,

    availableToDonate = availableToDonate,

    lastDonationAt = lastDonationAt,
    nextEligibleAt = nextEligibleAt,

    gdprAccepted = gdprAccepted,
    gdprAcceptedAt = gdprAcceptedAt,

    createdAt = createdAt,
    updatedAt = updatedAt
)