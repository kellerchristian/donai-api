package com.donai.api.domain.user

import com.donai.api.domain.shared.BloodType
import com.donai.api.domain.shared.GeoLocation
import java.time.Instant

data class User(
    val id: String,
    val firebaseUid: String,
    val name: String,
    val email: String,

    val bloodType: BloodType,
    val location: GeoLocation,
    val locationDisplay: String,

    val availableToDonate: Boolean,

    val lastDonationAt: Instant?,
    val nextEligibleAt: Instant?,

    val gdprAccepted: Boolean,
    val gdprAcceptedAt: Instant?,

    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant?
)