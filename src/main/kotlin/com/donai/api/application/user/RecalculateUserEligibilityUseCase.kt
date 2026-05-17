package com.donai.api.application.user

import com.donai.api.domain.matching.DonorEligibilityPolicy
import com.donai.api.domain.user.UserRepository
import java.time.Instant

class RecalculateUserEligibilityUseCase(
    private val userRepository: UserRepository,
    private val donorEligibilityPolicy: DonorEligibilityPolicy
) {

    operator fun invoke(
        userId: String,
        donatedAt: Instant
    ) {

        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException(
                "User not found"
            )

        val nextEligibleAt =
            donorEligibilityPolicy.calculateNextEligibleAt(
                donatedAt
            )

        val updated = userRepository.updateDonationStatus(
            userId = user.id,
            lastDonationAt = donatedAt,
            nextEligibleAt = nextEligibleAt,
            availableToDonate = false
        )

        if (!updated) {
            throw IllegalStateException(
                "Failed to update donor eligibility"
            )
        }
    }
}