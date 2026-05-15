package com.donai.api.infrastructure.db.mappers

import com.donai.api.domain.event.DonationEvent
import com.donai.api.infrastructure.db.tables.DonationEventsTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant

fun ResultRow.toDonationEvent() = DonationEvent(
    id = this[DonationEventsTable.id],

    donorId = this[DonationEventsTable.donorId],

    requestId = this[DonationEventsTable.requestId],

    commitmentId = this[DonationEventsTable.commitmentId],

    donatedAt = this[DonationEventsTable.donatedAt],

    createdAt = this[DonationEventsTable.createdAt]
)