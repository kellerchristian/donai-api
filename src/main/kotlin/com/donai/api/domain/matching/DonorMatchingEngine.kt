package com.donai.api.domain.matching

import com.donai.api.domain.request.DonationRequest

class DonorMatchingEngine(
    private val bloodCompatibilityPolicy: BloodCompatibilityPolicy,
    private val donorAvailabilityPolicy: DonorAvailabilityPolicy
) {

    fun match(
        request: DonationRequest,
        candidates: List<MatchingDonorCandidate>
    ): List<MatchingDonor> {

        return candidates
            .filter {
                donorAvailabilityPolicy.isAvailable(it.user)
            }
            .filter {
                bloodCompatibilityPolicy.isCompatible(
                    donor = it.user.bloodType,
                    recipient = request.bloodType
                )
            }
            .map {
                MatchingDonor(
                    donorId = it.user.id,
                    distanceMeters = it.distanceMeters
                )
            }
            .sortedBy { it.distanceMeters }
    }
}