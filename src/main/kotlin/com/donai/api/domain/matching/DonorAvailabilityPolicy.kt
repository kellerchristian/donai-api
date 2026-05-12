package com.donai.api.domain.matching

import com.donai.api.domain.user.User

class DonorAvailabilityPolicy {

    fun isAvailable(user: User): Boolean {
        return user.availableToDonate
    }
}