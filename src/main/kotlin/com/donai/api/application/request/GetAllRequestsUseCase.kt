package com.donai.api.application.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository

class GetAllRequestsUseCase(
    private val repository: RequestRepository
) {
    operator fun invoke(): List<DonationRequest> {
        return repository.getAll()
    }
}