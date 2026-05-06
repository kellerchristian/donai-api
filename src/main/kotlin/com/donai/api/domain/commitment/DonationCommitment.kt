package com.donai.api.domain.commitment

import java.time.Instant

data class DonationCommitment(
    val id: String,
    val requestId: String,
    val donorId: String,
    val status: CommitmentStatus,
    val aptitudeResponses: String?,
    val acceptedAt: Instant,
    val confirmedAt: Instant?
)