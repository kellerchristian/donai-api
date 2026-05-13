package com.donai.api.application.request

import com.donai.api.application.flow.HandleRequestCreatedFlow
import com.donai.api.domain.request.DonationRequest

class CreateRequestService(
    private val createRequestUseCase: CreateRequestUseCase,
    private val handleRequestCreatedFlow: HandleRequestCreatedFlow
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

        val request = createRequestUseCase(
            requesterId,
            bloodGroup,
            rhFactor,
            quantity,
            lat,
            lng,
            description
        )

        handleRequestCreatedFlow(request.id)

        return request
    }
}