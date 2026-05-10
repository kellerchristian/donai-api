package com.donai.api.presentation.dto.commitment

import com.donai.api.domain.commitment.CommitmentStatus
import com.donai.api.domain.commitment.DonationCommitment
import kotlinx.serialization.Serializable

@Serializable
data class CommitmentResponse(
    val id: String,
    val requestId: String,
    val donorId: String,
    val status: CommitmentStatus,
    val acceptedAt: Long,
    val confirmedAt: Long?
)

fun DonationCommitment.toResponse() = CommitmentResponse(
    id = id,
    requestId = requestId,
    donorId = donorId,
    status = status,
    acceptedAt = acceptedAt.toEpochMilli(),
    confirmedAt = confirmedAt?.toEpochMilli()
)