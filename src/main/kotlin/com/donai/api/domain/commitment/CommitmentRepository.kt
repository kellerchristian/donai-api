package com.donai.api.domain.commitment

import java.time.Instant

interface CommitmentRepository {

    fun create(commitment: DonationCommitment): DonationCommitment

    fun findById(id: String): DonationCommitment?

    fun findByRequestIdAndDonorId(
        requestId: String,
        donorId: String
    ): DonationCommitment?

    fun countConfirmedByRequestId(requestId: String): Int

    fun updateStatusAndConfirm(
        id: String,
        status: CommitmentStatus,
        confirmedAt: Instant?
    )
}