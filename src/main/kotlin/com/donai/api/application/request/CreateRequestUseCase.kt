package com.donai.api.application.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.request.RequestStatus
import com.donai.api.domain.shared.BloodGroup
import com.donai.api.domain.shared.RhFactor
import java.util.UUID

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
            id = UUID.randomUUID().toString(),
            requesterId = requesterId,

            requiredBloodGroup = BloodGroup.valueOf(bloodGroup),
            requiredRhFactor = RhFactor.valueOf(rhFactor),

            quantityNeeded = quantity,
            confirmedDonors = 0,

            locationLat = lat,
            locationLng = lng,

            description = description,
            status = RequestStatus.ACTIVE
        )

        return repository.create(request)
    }
}