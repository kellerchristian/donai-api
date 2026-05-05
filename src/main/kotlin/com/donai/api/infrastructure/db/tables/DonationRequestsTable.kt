package com.donai.api.infrastructure.db.tables

import org.jetbrains.exposed.sql.Table

object DonationRequestsTable : Table("donation_requests") {

    val id = varchar("id", 50)
    val requesterId = varchar("requester_id", 50)

    val requiredBloodGroup = varchar("required_blood_group", 2)
    val requiredRhFactor = varchar("required_rh_factor", 10)

    val quantityNeeded = integer("quantity_needed")
    val confirmedDonors = integer("confirmed_donors")

    // ⚠️ TEMPORAL (hasta usar PostGIS bien)
    val locationLat = double("location_lat")
    val locationLng = double("location_lng")

    val description = text("description")
    val status = varchar("status", 20)

    override val primaryKey = PrimaryKey(id)
}