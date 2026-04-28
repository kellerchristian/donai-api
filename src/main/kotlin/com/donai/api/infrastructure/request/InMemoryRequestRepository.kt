package com.donai.api.infrastructure.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository

class InMemoryRequestRepository : RequestRepository {

    private val requests = mutableListOf<DonationRequest>()

    override fun create(request: DonationRequest): DonationRequest {
        val saved = request.copy(id = (requests.size + 1).toString())
        requests.add(saved)
        return saved
    }

    override fun getAll(): List<DonationRequest> {
        return requests
    }
}