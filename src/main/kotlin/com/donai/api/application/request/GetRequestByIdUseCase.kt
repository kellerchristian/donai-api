package com.donai.api.application.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository

class GetRequestByIdUseCase(
    private val repository: RequestRepository
) {
    operator fun invoke(id: String): DonationRequest? {
        return repository.getById(id)
    }
}