package com.donai.api.application.request

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.request.RequestStatus

class CloseRequestIfCompletedUseCase(
    private val requestRepository: RequestRepository,
    private val commitmentRepository: CommitmentRepository
) {

    operator fun invoke(
        requestId: String
    ) {

        val request = requestRepository.getById(requestId)
            ?: throw IllegalArgumentException(
                "Request not found"
            )

        // 🔥 SOURCE OF TRUTH
        val confirmedCount =
            commitmentRepository.countConfirmedByRequestId(
                requestId
            )

        // 🔥 SINCRONIZAR CACHE
        requestRepository.updateConfirmedDonors(
            requestId = requestId,
            confirmedDonors = confirmedCount
        )

        // 🔥 CERRAR SI COMPLETA
        if (confirmedCount >= request.quantityNeeded) {

            requestRepository.updateStatus(
                id = requestId,
                status = RequestStatus.COMPLETED
            )
        }
    }
}