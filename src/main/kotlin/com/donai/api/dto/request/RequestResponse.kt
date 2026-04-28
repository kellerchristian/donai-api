package com.donai.api.dto.request

import com.donai.api.domain.request.DonationRequest
import kotlinx.serialization.Serializable

@Serializable
data class RequestResponse(
    val id: String,
    val status: String,
    val accepted: Int
)

fun DonationRequest.toResponse() = RequestResponse(
    id = id,
    status = status,
    accepted = accepted
)