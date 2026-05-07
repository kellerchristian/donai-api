package com.donai.api.application.commitment

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.commitment.CommitmentStateMachine
import com.donai.api.domain.commitment.CommitmentStatus

class SubmitAptitudeUseCase(
    private val commitmentRepository: CommitmentRepository
) {

    fun execute(
        commitmentId: String,
        responses: String
    ): CommitmentStatus {

        val commitment = commitmentRepository.findById(commitmentId)
            ?: throw IllegalArgumentException("Commitment not found")

        val isFit = evaluateAptitude(responses)

        val targetStatus = if (isFit) {
            CommitmentStatus.FIT
        } else {
            CommitmentStatus.UNFIT
        }

        CommitmentStateMachine.requireTransition(
            commitment.status,
            targetStatus
        )

        commitmentRepository.updateAptitude(
            id = commitment.id,
            status = targetStatus,
            aptitudeResponses = responses
        )

        return targetStatus
    }

    private fun evaluateAptitude(responses: String): Boolean {

        val normalized = responses.lowercase()

        if ("tattoo" in normalized) return false
        if ("fever" in normalized) return false
        if ("drug" in normalized) return false

        return true
    }
}