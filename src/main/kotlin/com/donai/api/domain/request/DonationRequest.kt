package com.donai.api.domain.request


data class DonationRequest(
    val id: String,
    val requesterId: String,
    val requiredBloodGroup: String,
    val requiredRhFactor: String,
    val quantityNeeded: Int,
    val confirmedDonors: Int,
    val locationLat: Double,
    val locationLng: Double,
    val description: String,
    val status: RequestStatus
)