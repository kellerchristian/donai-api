package com.donai.api.domain.commitment

interface CommitmentRepository {

    fun create(commitment: DonationCommitment): DonationCommitment

    fun findByRequestIdAndDonorId(
        requestId: String,
        donorId: String
    ): DonationCommitment?

    fun countConfirmedByRequestId(requestId: String): Int
}