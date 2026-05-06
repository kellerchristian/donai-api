package com.donai.api.infrastructure.db.tables

import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.Table


object DonationCommitmentsTable : Table("donation_commitments") {

    val id = varchar("id", 50)

    val requestId = varchar("request_id", 50)
    val donorId = varchar("donor_id", 50)

    val status = varchar("status", 30)

    val aptitudeResponses = text("aptitude_responses").nullable()

    val acceptedAt = timestamp("accepted_at")
    val confirmedAt = timestamp("confirmed_at").nullable()

    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)

    init {
        index(true, donorId, requestId) // UNIQUE constraint
    }
}