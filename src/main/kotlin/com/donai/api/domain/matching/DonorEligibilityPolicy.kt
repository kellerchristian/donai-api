package com.donai.api.domain.matching

import com.donai.api.domain.user.User
import java.time.Instant

class DonorEligibilityPolicy {

    fun isEligible(user: User, now: Instant = Instant.now()): Boolean {

        // 1. Bloqueo manual del usuario
        if (!user.availableToDonate) return false

        // 2. Cooldown por última donación
        val nextEligibleAt = user.nextEligibleAt
        if (nextEligibleAt != null && nextEligibleAt.isAfter(now)) {
            return false
        }

        // 3. Seguridad extra (opcional defensivo)
        if (user.deletedAt != null) return false

        return true
    }
}