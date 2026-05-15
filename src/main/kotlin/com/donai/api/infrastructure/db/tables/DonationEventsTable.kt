package com.donai.api.infrastructure.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object DonationEventsTable : Table("donation_events") {

    val id = varchar("id", 36)

    val donorId = varchar("donor_id", 36)

    val requestId = varchar("request_id", 36)

    val commitmentId = varchar("commitment_id", 36)

    val donatedAt = timestamp("donated_at")

    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}