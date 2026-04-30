package com.donai.api.infrastructure.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import com.donai.api.infrastructure.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class PostgresRequestRepository : RequestRepository {

    override fun create(request: DonationRequest): DonationRequest  = dbQuery{
        DonationRequestsTable.insert {
            it[id] = request.id
            it[requesterId] = request.requesterId
            it[bloodGroup] = request.bloodGroup
            it[rhFactor] = request.rhFactor
            it[quantity] = request.quantity
            it[accepted] = request.accepted
            it[locationLat] = request.locationLat
            it[locationLng] = request.locationLng
            it[description] = request.description
            it[status] = request.status
        }

        request
    }

    override fun getAll(): List<DonationRequest> {
        return DonationRequestsTable.selectAll().map {
            DonationRequest(
                id = it[DonationRequestsTable.id],
                requesterId = it[DonationRequestsTable.requesterId],
                bloodGroup = it[DonationRequestsTable.bloodGroup],
                rhFactor = it[DonationRequestsTable.rhFactor],
                quantity = it[DonationRequestsTable.quantity],
                accepted = it[DonationRequestsTable.accepted],
                locationLat = it[DonationRequestsTable.locationLat],
                locationLng = it[DonationRequestsTable.locationLng],
                description = it[DonationRequestsTable.description],
                status = it[DonationRequestsTable.status]
            )
        }
    }
}