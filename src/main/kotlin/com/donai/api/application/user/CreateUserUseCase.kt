package com.donai.api.application.user

import com.donai.api.domain.shared.BloodGroup
import com.donai.api.domain.shared.BloodType
import com.donai.api.domain.shared.GeoLocation
import com.donai.api.domain.shared.RhFactor
import com.donai.api.domain.user.User
import com.donai.api.domain.user.UserRepository
import java.time.Instant
import java.util.UUID

class CreateUserUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(
        firebaseUid: String,
        name: String,
        email: String,
        bloodGroup: String,
        rhFactor: String,
        latitude: Double,
        longitude: Double,
        locationDisplay: String,
        gdprAccepted: Boolean
    ): User {

        val existingUser = userRepository
            .findByFirebaseUid(firebaseUid)

        if (existingUser != null) {
            throw IllegalArgumentException(
                "User already exists"
            )
        }

        val now = Instant.now()

        val user = User(
            id = UUID.randomUUID().toString(),

            firebaseUid = firebaseUid,

            name = name,

            email = email,

            bloodType = BloodType(
                bloodGroup = BloodGroup.valueOf(bloodGroup),
                rhFactor = RhFactor.valueOf(rhFactor)
            ),

            location = GeoLocation(
                latitude = latitude,
                longitude = longitude
            ),

            locationDisplay = locationDisplay,

            availableToDonate = true,

            lastDonationAt = null,

            nextEligibleAt = null,

            gdprAccepted = gdprAccepted,

            gdprAcceptedAt = if (gdprAccepted) {
                now
            } else {
                null
            },

            createdAt = now,

            updatedAt = now,

            deletedAt = null
        )

        return userRepository.create(user)
    }
}