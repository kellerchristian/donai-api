package com.donai.api.application.commitment

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.commitment.DonationCommitment
import com.donai.api.domain.commitment.CommitmentStatus
import com.donai.api.domain.request.RequestRepository
import java.time.Instant
import java.util.UUID

class CreateCommitmentUseCase(
    private val requestRepository: RequestRepository,
    private val commitmentRepository: CommitmentRepository
) {

    fun execute(requestId: String, donorId: String): DonationCommitment {

        // 1. Verificar que la request existe
        val request = requestRepository.getById(requestId)
            ?: throw IllegalArgumentException("Request not found")

        // 2. Verificar estado ACTIVE
        if (request.status != com.donai.api.domain.request.RequestStatus.ACTIVE) {
            throw IllegalStateException("Request is not active")
        }

        // 3. Verificar que NO está llena
        if (request.confirmedDonors >= request.quantityNeeded) {
            throw IllegalStateException("Request is already full")
        }

        // 4. Verificar duplicado donor-request
        val existing = commitmentRepository.findByRequestIdAndDonorId(
            requestId,
            donorId
        )

        if (existing != null) {
            throw IllegalStateException("Commitment already exists for this donor and request")
        }

        // 5. Crear commitment
        val now = Instant.now()
        val commitment = DonationCommitment(
            id = UUID.randomUUID().toString(),
            requestId = requestId,
            donorId = donorId,
            status = CommitmentStatus.PENDING_APTITUDE,
            aptitudeResponses = null,
            acceptedAt = now,
            confirmedAt = null
        )

        return commitmentRepository.create(commitment)
    }
}