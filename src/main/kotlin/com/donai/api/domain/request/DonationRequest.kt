package com.donai.api.domain.request

import com.donai.api.domain.shared.BloodType
import com.donai.api.domain.shared.GeoLocation


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
){
    val bloodType: BloodType
        get() = BloodType(
            bloodGroup = requiredBloodGroup,
            rhFactor = requiredRhFactor
        )

    val location: GeoLocation
        get() = GeoLocation(
            latitude = locationLat,
            longitude = locationLng
        )
}