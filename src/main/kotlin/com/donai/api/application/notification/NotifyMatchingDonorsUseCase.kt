package com.donai.api.application.notification

import com.donai.api.domain.matching.MatchingDonor
import com.donai.api.domain.request.RequestRepository

class NotifyMatchingDonorsUseCase(
    private val requestRepository: RequestRepository
) {

    operator fun invoke(
        requestId: String,
        donors: List<MatchingDonor>
    ) {

        val request = requestRepository.getById(requestId)
            ?: throw IllegalArgumentException("Request not found")

        if (donors.isEmpty()) {
            println("📭 No matching donors for request $requestId")
            return
        }

        donors.forEach { donor ->

            // 🔔 MOCK NOTIFICATION (futuro: push/email/sms)
            println(
                """
                🔔 NOTIFICATION SENT
                --------------------
                To donor: ${donor.donorId}
                For request: $requestId
                Blood needed: ${request.bloodType}
                Distance: ${"%.2f".format(donor.distanceMeters)} m
                --------------------
                """.trimIndent()
            )
        }

        println("✅ Total notifications sent: ${donors.size}")
    }
}