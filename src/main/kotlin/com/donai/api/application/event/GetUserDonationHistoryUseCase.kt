package com.donai.api.application.event

import com.donai.api.domain.event.DonationEvent
import com.donai.api.domain.event.DonationEventRepository

class GetUserDonationHistoryUseCase(
    private val repository: DonationEventRepository
) {

    operator fun invoke(userId: String): List<DonationEvent> {
        return repository.findByUserId(userId)
    }
}