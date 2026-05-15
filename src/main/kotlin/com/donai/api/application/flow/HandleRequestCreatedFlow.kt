package com.donai.api.application.flow

import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.application.notification.NotifyMatchingDonorsUseCase

class HandleRequestCreatedFlow(
    private val findMatchingDonorsUseCase: FindMatchingDonorsUseCase,
    private val notifyMatchingDonorsUseCase: NotifyMatchingDonorsUseCase
) {
    operator fun invoke(
        requestId: String
    ) {

        println("🚀 Starting request created flow for request: $requestId")

        val matchingDonors = findMatchingDonorsUseCase(
            requestId = requestId
        )

        println("🩸 Matching donors found: ${matchingDonors.size}")

        notifyMatchingDonorsUseCase(
            requestId = requestId,
            donors = matchingDonors
        )

        println("✅ Request created flow completed")
    }
}