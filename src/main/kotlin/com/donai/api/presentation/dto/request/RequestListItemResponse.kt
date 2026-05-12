package com.donai.api.presentation.dto.request

import com.donai.api.domain.request.DonationRequest
import kotlinx.serialization.Serializable

@Serializable
data class RequestListItemResponse(
    val id: String,

    val bloodGroup: String,
    val rhFactor: String,

    val quantityNeeded: Int,
    val confirmedDonors: Int,

    val status: String
)

fun DonationRequest.toListItemResponse() = RequestListItemResponse(
    id = id,

    bloodGroup = requiredBloodGroup.name,
    rhFactor = requiredRhFactor.name,

    quantityNeeded = quantityNeeded,
    confirmedDonors = confirmedDonors,

    status = status.name
)