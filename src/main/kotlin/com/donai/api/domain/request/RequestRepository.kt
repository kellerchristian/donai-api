package com.donai.api.domain.request

interface RequestRepository {
    fun create(request: DonationRequest): DonationRequest
    fun getAll(): List<DonationRequest>
}