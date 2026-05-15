package com.donai.api.application.event

import com.donai.api.domain.event.DonationEvent
import com.donai.api.domain.event.DonationEventRepository
import java.time.Instant
import java.util.UUID

class RegisterDonationEventUseCase(
    private val repository: DonationEventRepository
) {

    operator fun invoke(
        donorId: String,
        requestId: String,
        commitmentId: String
    ): DonationEvent {

        val event = DonationEvent(
            id = UUID.randomUUID().toString(),
            donorId = donorId,
            requestId = requestId,
            commitmentId = commitmentId,
            donatedAt = Instant.now(),
            createdAt = Instant.now()
        )

        repository.save(event)

        return event
    }
}