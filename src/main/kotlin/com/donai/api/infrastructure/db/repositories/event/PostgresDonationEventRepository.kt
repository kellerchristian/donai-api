package com.donai.api.infrastructure.db.repositories.event

import com.donai.api.domain.event.DonationEvent
import com.donai.api.domain.event.DonationEventRepository
import com.donai.api.infrastructure.db.dbQuery
import com.donai.api.infrastructure.db.mappers.toDonationEvent
import com.donai.api.infrastructure.db.tables.DonationEventsTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class PostgresDonationEventRepository : DonationEventRepository {

    override fun save(event: DonationEvent) {

        dbQuery {

            DonationEventsTable.insert {

                it[id] = event.id

                it[donorId] = event.donorId

                it[requestId] = event.requestId

                it[commitmentId] = event.commitmentId

                it[donatedAt] = event.donatedAt

                it[createdAt] = event.createdAt
            }
        }
    }

    override fun findByUserId(
        userId: String
    ): List<DonationEvent> {

        return dbQuery {

            DonationEventsTable
                .selectAll()
                .where {
                    DonationEventsTable.donorId eq userId
                }
                .orderBy(
                    DonationEventsTable.donatedAt,
                    SortOrder.DESC
                )
                .map {
                    it.toDonationEvent()
                }
        }
    }
}