package com.donai.api.application.commitment

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.commitment.CommitmentStateMachine
import com.donai.api.domain.commitment.CommitmentStatus
import java.time.Instant

class ConfirmCommitmentUseCase(
    private val commitmentRepository: CommitmentRepository
) {

    operator fun invoke(
        commitmentId: String
    ) {

        val commitment = commitmentRepository.findById(commitmentId)
            ?: throw IllegalArgumentException(
                "Commitment not found"
            )

        // 🔒 IDEMPOTENCIA
        if (commitment.status == CommitmentStatus.CONFIRMED) {
            return
        }

        // 🔒 VALIDAR TRANSICIÓN
        CommitmentStateMachine.requireTransition(
            commitment.status,
            CommitmentStatus.CONFIRMED
        )

        // 🔥 CONFIRMAR
        commitmentRepository.updateStatus(
            id = commitment.id,
            status = CommitmentStatus.CONFIRMED,
            confirmedAt = Instant.now()
        )
    }
}