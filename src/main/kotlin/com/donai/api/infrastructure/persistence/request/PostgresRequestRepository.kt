package com.donai.api.infrastructure.persistence.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import com.donai.api.infrastructure.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class PostgresRequestRepository : RequestRepository {

    override fun create(request: DonationRequest): DonationRequest = dbQuery {

        DonationRequestsTable.insert {
            it[id] = request.id
            it[requesterId] = request.requesterId
            it[requiredBloodGroup] = request.requiredBloodGroup
            it[requiredRhFactor] = request.requiredRhFactor
            it[quantityNeeded] = request.quantityNeeded
            it[confirmedDonors] = request.confirmedDonors
            it[locationLat] = request.locationLat
            it[locationLng] = request.locationLng
            it[description] = request.description
            it[status] = request.status
        }
        request
    }

    override fun getAll(): List<DonationRequest> = dbQuery {
        DonationRequestsTable
            .selectAll()
            .map { RequestMapper.fromRow(it) }
    }

    override fun getById(id: String): DonationRequest? = dbQuery {
        DonationRequestsTable
            .select { DonationRequestsTable.id eq id }
            .map { RequestMapper.fromRow(it) }
            .singleOrNull()
    }

    override fun getFeedForUser(userId: String): List<DonationRequest> = dbQuery {
        DonationRequestsTable.selectAll().map {
            RequestMapper.fromRow(it)
        }
    }
}