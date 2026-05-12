package com.donai.api.application.user

import com.donai.api.domain.user.UserRepository

class UpdateDonationAvailabilityUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        userId: String,
        availableToDonate: Boolean
    ) {
        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException(
                "User not found"
            )
        if (user.deletedAt != null) {
            throw IllegalArgumentException(
                "User is deleted"
            )
        }
        val updated = userRepository.updateAvailability(
            userId = userId,
            availableToDonate = availableToDonate
        )
        if (!updated) {
            throw IllegalArgumentException("User not found")
        }
    }
}