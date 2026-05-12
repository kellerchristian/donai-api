package com.donai.api.domain.matching

import com.donai.api.domain.shared.BloodGroup
import com.donai.api.domain.shared.BloodType
import com.donai.api.domain.shared.RhFactor

class BloodCompatibilityPolicy {

    fun isCompatible(
        donor: BloodType,
        recipient: BloodType
    ): Boolean {

        val groupCompatible = when (recipient.bloodGroup) {

            BloodGroup.O ->
                donor.bloodGroup == BloodGroup.O

            BloodGroup.A ->
                donor.bloodGroup == BloodGroup.A ||
                        donor.bloodGroup == BloodGroup.O

            BloodGroup.B ->
                donor.bloodGroup == BloodGroup.B ||
                        donor.bloodGroup == BloodGroup.O

            BloodGroup.AB ->
                true
        }

        val rhCompatible = when (recipient.rhFactor) {

            RhFactor.POSITIVE ->
                true

            RhFactor.NEGATIVE ->
                donor.rhFactor == RhFactor.NEGATIVE
        }

        return groupCompatible && rhCompatible
    }
}