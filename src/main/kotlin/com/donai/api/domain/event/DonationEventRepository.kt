package com.donai.api.domain.event

interface DonationEventRepository {

    fun save(event: DonationEvent)

    fun findByUserId(userId: String): List<DonationEvent>
}