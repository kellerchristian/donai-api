package com.donai.api.application.request

import com.donai.api.domain.matching.BloodCompatibilityPolicy
import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.user.UserRepository

class GetFeedRequestsUseCase(
    private val requestRepository: RequestRepository,
    private val userRepository: UserRepository,
    private val bloodCompatibilityPolicy: BloodCompatibilityPolicy
) {

    companion object {
        private const val DEFAULT_RADIUS_METERS = 50000.0
    }

    operator fun invoke(
        userId: String,
        radiusMeters: Double? = null
    ): List<DonationRequest> {

        val user = userRepository.findById(userId)
            ?: throw IllegalArgumentException("User not found")

        val radius = radiusMeters ?: DEFAULT_RADIUS_METERS

        val activeRequests = requestRepository.getAll()
            .filter { it.status.name == "ACTIVE" }

        return activeRequests
            .filter { request ->

                // 🩸 compatibilidad sanguínea
                bloodCompatibilityPolicy.isCompatible(
                    donor = user.bloodType,
                    recipient = request.bloodType
                )
            }
            .filter { request ->

                // 📍 distancia simple (mock/future PostGIS)
                val distance = haversineMeters(
                    user.location.latitude,
                    user.location.longitude,
                    request.location.latitude,
                    request.location.longitude
                )

                distance <= radius
            }
            .sortedBy { request ->
                haversineMeters(
                    user.location.latitude,
                    user.location.longitude,
                    request.location.latitude,
                    request.location.longitude
                )
            }
    }

    private fun haversineMeters(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {

        val earthRadius = 6371000.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a =
            kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) +
                    kotlin.math.cos(Math.toRadians(lat1)) *
                    kotlin.math.cos(Math.toRadians(lat2)) *
                    kotlin.math.sin(dLon / 2) *
                    kotlin.math.sin(dLon / 2)

        val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))

        return earthRadius * c
    }
}