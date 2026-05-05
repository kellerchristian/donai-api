package com.donai.api.infrastructure.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import com.donai.api.infrastructure.dbQuery
import org.jetbrains.exposed.sql.insert
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

        DonationRequestsTable.selectAll().map {

            DonationRequest(
                id = it[DonationRequestsTable.id],
                requesterId = it[DonationRequestsTable.requesterId],
                requiredBloodGroup = it[DonationRequestsTable.requiredBloodGroup],
                requiredRhFactor = it[DonationRequestsTable.requiredRhFactor],
                quantityNeeded = it[DonationRequestsTable.quantityNeeded],
                confirmedDonors = it[DonationRequestsTable.confirmedDonors],
                locationLat = it[DonationRequestsTable.locationLat],
                locationLng = it[DonationRequestsTable.locationLng],
                description = it[DonationRequestsTable.description],
                status = it[DonationRequestsTable.status]
            )
        }
    }
}