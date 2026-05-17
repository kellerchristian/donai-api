package com.donai.api.application.flow

import com.donai.api.application.event.RegisterDonationEventUseCase
import com.donai.api.application.request.CloseRequestIfCompletedUseCase
import com.donai.api.application.user.RecalculateUserEligibilityUseCase
import com.donai.api.domain.commitment.CommitmentRepository

class OnCommitmentConfirmedFlow(
    private val commitmentRepository: CommitmentRepository,
    private val registerDonationEventUseCase: RegisterDonationEventUseCase,
    private val recalculateUserEligibilityUseCase: RecalculateUserEligibilityUseCase,
    private val closeRequestIfCompletedUseCase: CloseRequestIfCompletedUseCase
) {

    operator fun invoke(
        commitmentId: String
    ) {

        val commitment = commitmentRepository.findById(commitmentId)
            ?: throw IllegalArgumentException(
                "Commitment not found"
            )

        val donationEvent = registerDonationEventUseCase(
            donorId = commitment.donorId,
            requestId = commitment.requestId,
            commitmentId = commitment.id
        )

        recalculateUserEligibilityUseCase(
            userId = commitment.donorId,
            donatedAt = donationEvent.donatedAt
        )

        closeRequestIfCompletedUseCase(
            requestId = commitment.requestId
        )
    }
}