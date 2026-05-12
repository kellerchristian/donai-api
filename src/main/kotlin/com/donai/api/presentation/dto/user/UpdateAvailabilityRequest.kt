package com.donai.api.presentation.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAvailabilityRequest(
    val availableToDonate: Boolean
)