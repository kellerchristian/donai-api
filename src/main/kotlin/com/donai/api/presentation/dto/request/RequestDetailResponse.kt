package com.donai.api.presentation.dto.request

import com.donai.api.domain.request.DonationRequest
import kotlinx.serialization.Serializable

@Serializable
data class RequestDetailResponse(
    val id: String,
    val requesterId: String,

    val bloodGroup: String,
    val rhFactor: String,

    val quantityNeeded: Int,
    val confirmedDonors: Int,

    val locationLat: Double,
    val locationLng: Double,

    val description: String,
    val status: String
)

fun DonationRequest.toDetailResponse() = RequestDetailResponse(
    id = id,
    requesterId = requesterId,

    bloodGroup = requiredBloodGroup.name,
    rhFactor = requiredRhFactor.name,

    quantityNeeded = quantityNeeded,
    confirmedDonors = confirmedDonors,

    locationLat = locationLat,
    locationLng = locationLng,

    description = description,
    status = status.name
)