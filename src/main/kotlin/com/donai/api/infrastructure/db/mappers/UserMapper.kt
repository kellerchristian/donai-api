package com.donai.api.infrastructure.db.mappers

import com.donai.api.domain.shared.BloodGroup
import com.donai.api.domain.shared.BloodType
import com.donai.api.domain.shared.GeoLocation
import com.donai.api.domain.shared.RhFactor
import com.donai.api.domain.user.User
import com.donai.api.infrastructure.db.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User {
    return User(
        id = this[UsersTable.id],

        firebaseUid = this[UsersTable.firebaseUid],

        name = this[UsersTable.name],

        email = this[UsersTable.email],

        bloodType = BloodType(
            bloodGroup = BloodGroup.valueOf(this[UsersTable.bloodGroup]),
            rhFactor = RhFactor.valueOf(this[UsersTable.rhFactor])
        ),

        location = GeoLocation(
            latitude = this[UsersTable.locationLat],
            longitude = this[UsersTable.locationLng]
        ),

        locationDisplay = this[UsersTable.locationDisplay],

        availableToDonate = this[UsersTable.availableToDonate],

        lastDonationAt = this[UsersTable.lastDonationAt],

        nextEligibleAt = this[UsersTable.nextEligibleAt],

        gdprAccepted = this[UsersTable.gdprAccepted],

        gdprAcceptedAt = this[UsersTable.gdprAcceptedAt],

        createdAt = this[UsersTable.createdAt],

        updatedAt = this[UsersTable.updatedAt],

        deletedAt = this[UsersTable.deletedAt]
    )
}