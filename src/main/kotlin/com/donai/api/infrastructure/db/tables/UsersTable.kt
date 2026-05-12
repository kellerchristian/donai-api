package com.donai.api.infrastructure.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object UsersTable : Table("users") {

    val id = varchar("id", 50)

    val firebaseUid = varchar("firebase_uid", 128)

    val name = varchar("name", 255)

    val email = varchar("email", 255)

    val bloodGroup = varchar("blood_group", 2)

    val rhFactor = varchar("rh_factor", 10)

    // TEMPORAL hasta integrar geography correctamente con Exposed
    val locationLat = double("location_lat")

    val locationLng = double("location_lng")

    val locationDisplay = varchar("location_display", 255)

    val availableToDonate = bool("available_to_donate")

    val lastDonationAt = timestamp("last_donation_at")
        .nullable()

    val nextEligibleAt = timestamp("next_eligible_at")
        .nullable()

    val gdprAccepted = bool("gdpr_accepted")

    val gdprAcceptedAt = timestamp("gdpr_accepted_at")
        .nullable()

    val createdAt = timestamp("created_at")

    val updatedAt = timestamp("updated_at")

    val deletedAt = timestamp("deleted_at")
        .nullable()

    override val primaryKey = PrimaryKey(id)
}