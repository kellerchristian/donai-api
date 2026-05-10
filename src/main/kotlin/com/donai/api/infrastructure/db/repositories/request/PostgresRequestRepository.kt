package com.donai.api.infrastructure.db.repositories.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository
import com.donai.api.domain.request.RequestStatus
import com.donai.api.infrastructure.db.mappers.RequestMapper
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import com.donai.api.infrastructure.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

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
            it[status] = request.status.name
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

    override fun updateStatus(id: String, status: RequestStatus) = dbQuery {
        val updatedRows = DonationRequestsTable
            .update({ DonationRequestsTable.id eq id }) {
                it[this.status] = status.name
            }

        if (updatedRows == 0) {
            throw IllegalArgumentException("Request not found")
        }
    }

    override fun updateConfirmedDonors(
        requestId: String,
        confirmedDonors: Int
    ) = dbQuery {

        val updatedRows = DonationRequestsTable
            .update({ DonationRequestsTable.id eq requestId }) {
                it[this.confirmedDonors] = confirmedDonors
            }

        if (updatedRows == 0) {
            throw IllegalArgumentException("Request not found")
        }
    }
}