package com.donai.api.application.commitment

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.commitment.CommitmentStateMachine
import com.donai.api.domain.commitment.CommitmentStatus

class CancelCommitmentUseCase(
    private val commitmentRepository: CommitmentRepository
) {

    fun execute(commitmentId: String) {

        val commitment = commitmentRepository.findById(commitmentId)
            ?: throw IllegalArgumentException("Commitment not found")

        // 🔒 idempotencia
        if (commitment.status == CommitmentStatus.CANCELLED) return

        // 🔒 no se puede cancelar si ya fue confirmado
        CommitmentStateMachine.requireTransition(
            commitment.status,
            CommitmentStatus.CANCELLED
        )

        commitmentRepository.updateStatus(
            id = commitment.id,
            status = CommitmentStatus.CANCELLED,
            confirmedAt = null
        )
    }
}