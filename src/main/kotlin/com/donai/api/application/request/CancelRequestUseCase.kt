package com.donai.api.application.request

import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.request.RequestStatus

class CancelRequestUseCase(
    private val repository: RequestRepository
) {
    operator fun invoke(id: String) {

        val request = repository.getById(id)
            ?: throw IllegalArgumentException("Request not found")

        if (request.status == RequestStatus.CANCELLED) return
        if (request.status == RequestStatus.COMPLETED)
            throw IllegalStateException("Cannot cancel a completed request")

        repository.updateStatus(id, RequestStatus.CANCELLED)
    }
}