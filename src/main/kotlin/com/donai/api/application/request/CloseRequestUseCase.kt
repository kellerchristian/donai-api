package com.donai.api.application.request

import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.request.RequestStatus

class CloseRequestUseCase(
    private val repository: RequestRepository
) {
    operator fun invoke(id: String) {

        val request = repository.getById(id)
            ?: throw IllegalArgumentException("Request not found")

        if (request.status != RequestStatus.ACTIVE) return

        repository.updateStatus(id, RequestStatus.CLOSED)
    }
}