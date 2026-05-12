package com.donai.api.infrastructure.db.repositories.matching

import com.donai.api.domain.matching.DonorRepository
import com.donai.api.domain.matching.MatchingDonorCandidate
import com.donai.api.domain.shared.GeoLocation
import com.donai.api.infrastructure.db.dbQuery
import com.donai.api.infrastructure.db.mappers.toUser
import com.donai.api.infrastructure.db.tables.UsersTable
import org.jetbrains.exposed.sql.selectAll
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class PostgresDonorRepository : DonorRepository {

    override fun findCandidatesNear(
        location: GeoLocation,
        radiusMeters: Double
    ): List<MatchingDonorCandidate> {

        return dbQuery {

            UsersTable
                .selectAll()
                .mapNotNull { row ->

                    val user = row.toUser()

                    val userLocation = user.location
                        ?: return@mapNotNull null

                    val distance = haversineMeters(
                        lat1 = location.latitude,
                        lon1 = location.longitude,
                        lat2 = userLocation.latitude,
                        lon2 = userLocation.longitude
                    )

                    if (distance > radiusMeters) {
                        return@mapNotNull null
                    }

                    MatchingDonorCandidate(
                        user = user,
                        distanceMeters = distance
                    )
                }
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
            sin(dLat / 2) * sin(dLat / 2) +
                    cos(Math.toRadians(lat1)) *
                    cos(Math.toRadians(lat2)) *
                    sin(dLon / 2) *
                    sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }
}