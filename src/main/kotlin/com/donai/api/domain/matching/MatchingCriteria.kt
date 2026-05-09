package com.donai.api.domain.matching

import com.donai.api.domain.shared.BloodType
import com.donai.api.domain.shared.GeoLocation

data class MatchingCriteria(
    val bloodType: BloodType,
    val location: GeoLocation,
    val radiusMeters: Double
)