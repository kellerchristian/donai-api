package com.donai.api.application.commitment

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.commitment.CommitmentStateMachine
import com.donai.api.domain.commitment.CommitmentStatus
import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.request.RequestStatus
import java.time.Instant

class ConfirmCommitmentUseCase(
    private val commitmentRepository: CommitmentRepository,
    private val requestRepository: RequestRepository
) {

    fun execute(commitmentId: String) {

        val commitment = commitmentRepository.findById(commitmentId)
            ?: throw IllegalArgumentException("Commitment not found")

        // 🔒 1. IDEMPOTENCIA
        if (commitment.status == CommitmentStatus.CONFIRMED) return

        // 🔒 2. VALIDAR TRANSICIÓN
        CommitmentStateMachine.requireTransition(
            commitment.status,
            CommitmentStatus.CONFIRMED
        )

        val now = Instant.now()

        // 🔥 3. Confirmar commitment
        commitmentRepository.updateStatusAndConfirm(
            id = commitment.id,
            status = CommitmentStatus.CONFIRMED,
            confirmedAt = now
        )

        // 🔥 4. Contar confirmados (fuente real)
        val confirmedCount = commitmentRepository
            .countConfirmedByRequestId(commitment.requestId)

        // sincronizar cache/denormalización
        requestRepository.updateConfirmedDonors(
            commitment.requestId,
            confirmedCount
        )

        val request = requestRepository.getById(commitment.requestId)
            ?: throw IllegalStateException("Request not found")

        // 🔥 5. Cerrar request si aplica
        if (confirmedCount >= request.quantityNeeded) {
            requestRepository.updateStatus(
                request.id,
                RequestStatus.COMPLETED
            )
        }
    }
}