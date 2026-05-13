package com.donai.api.application.flow

import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.application.notification.NotifyMatchingDonorsUseCase

class HandleRequestCreatedFlow(
    private val findMatchingDonorsUseCase: FindMatchingDonorsUseCase,
    private val notifyMatchingDonorsUseCase: NotifyMatchingDonorsUseCase
) {

    companion object {
        private const val DEFAULT_RADIUS_METERS = 50000.0
    }

    operator fun invoke(
        requestId: String
    ) {

        println("🚀 Starting request created flow for request: $requestId")

        val matchingDonors = findMatchingDonorsUseCase(
            requestId = requestId,
            radiusMeters = DEFAULT_RADIUS_METERS
        )

        println("🩸 Matching donors found: ${matchingDonors.size}")

        notifyMatchingDonorsUseCase(
            requestId = requestId,
            donors = matchingDonors
        )

        println("✅ Request created flow completed")
    }
}