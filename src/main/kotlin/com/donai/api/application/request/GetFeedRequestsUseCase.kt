package com.donai.api.application.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository

class GetFeedRequestsUseCase(
    private val repository: RequestRepository
) {

    operator fun invoke(userId: String): List<DonationRequest> {
        return repository.getFeedForUser(userId)
    }
}