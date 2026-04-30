package com.donai.api.infrastructure.db.tables

import org.jetbrains.exposed.sql.Table

object DonationRequestsTable : Table("donation_requests") {

    val id = varchar("id", 50)
    val requesterId = varchar("requester_id", 50)

    val bloodGroup = varchar("blood_group", 10)
    val rhFactor = varchar("rh_factor", 10)

    val quantity = integer("quantity")
    val accepted = integer("accepted").default(0)

    val locationLat = double("location_lat")
    val locationLng = double("location_lng")

    val description = text("description")
    val status = varchar("status", 20)

    override val primaryKey = PrimaryKey(id)
}