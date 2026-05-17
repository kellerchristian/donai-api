package com.donai.api.domain.matching

import com.donai.api.domain.user.User
import java.time.Instant
import java.time.temporal.ChronoUnit

class DonorEligibilityPolicy {

//    fun isEligible(user: User, now: Instant = Instant.now()): Boolean {
//
//        // 1. Bloqueo manual del usuario
//        if (!user.availableToDonate) return false
//
//        // 2. Cooldown por última donación
//        val nextEligibleAt = user.nextEligibleAt
//        if (nextEligibleAt != null && nextEligibleAt.isAfter(now)) {
//            return false
//        }
//
//        // 3. Seguridad extra (opcional defensivo)
//        if (user.deletedAt != null) return false
//
//        return true
//    }
companion object {
    private const val MIN_DAYS_BETWEEN_DONATIONS = 90L
}

    fun isEligible(user: User): Boolean {

        if (!user.availableToDonate) {
            return false
        }

        val nextEligibleAt = user.nextEligibleAt
            ?: return true

        return !nextEligibleAt.isAfter(Instant.now())
    }

    fun calculateNextEligibleAt(
        donatedAt: Instant
    ): Instant {

        return donatedAt.plus(
            MIN_DAYS_BETWEEN_DONATIONS,
            ChronoUnit.DAYS
        )
    }
}