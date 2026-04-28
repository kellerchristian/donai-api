package com.donai.api.application.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository

class CreateRequestUseCase(
    private val repository: RequestRepository
) {

    operator fun invoke(
        requesterId: String,
        bloodGroup: String,
        rhFactor: String,
        quantity: Int,
        lat: Double,
        lng: Double,
        description: String
    ): DonationRequest {

        val request = DonationRequest(
            id = "",
            requesterId = requesterId,
            bloodGroup = bloodGroup,
            rhFactor = rhFactor,
            quantity = quantity,
            locationLat = lat,
            locationLng = lng,
            description = description
        )

        return repository.create(request)
    }
}