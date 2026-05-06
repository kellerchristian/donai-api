package com.donai.api.domain.request

interface RequestRepository {
    fun create(request: DonationRequest): DonationRequest
    fun getAll(): List<DonationRequest>
    fun getById(id: String): DonationRequest?
    fun getFeedForUser(userId: String): List<DonationRequest>
    fun updateStatus(id: String, status: RequestStatus)
}