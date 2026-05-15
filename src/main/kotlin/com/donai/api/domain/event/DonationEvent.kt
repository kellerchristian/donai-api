package com.donai.api.domain.event

import java.time.Instant

data class DonationEvent(
    val id: String,

    val donorId: String,
    val requestId: String,
    val commitmentId: String,

    val donatedAt: Instant,

    val createdAt: Instant
)