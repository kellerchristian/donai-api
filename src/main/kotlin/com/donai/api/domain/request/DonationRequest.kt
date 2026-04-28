package com.donai.api.domain.request


data class DonationRequest(
    val id: String,
    val requesterId: String,
    val bloodGroup: String,
    val rhFactor: String,
    val quantity: Int,
    val accepted: Int = 0,
    val locationLat: Double,
    val locationLng: Double,
    val description: String,
    val status: String = "ACTIVE",
    val createdAt: Long = System.currentTimeMillis()
)