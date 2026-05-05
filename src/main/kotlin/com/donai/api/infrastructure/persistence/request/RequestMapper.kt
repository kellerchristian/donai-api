package com.donai.api.infrastructure.persistence.request

import com.donai.api.domain.request.DonationRequest
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import org.jetbrains.exposed.sql.ResultRow

object RequestMapper {

    fun fromRow(row: ResultRow): DonationRequest {
        return DonationRequest(
            id = row[DonationRequestsTable.id],
            requesterId = row[DonationRequestsTable.requesterId],
            requiredBloodGroup = row[DonationRequestsTable.requiredBloodGroup],
            requiredRhFactor = row[DonationRequestsTable.requiredRhFactor],
            quantityNeeded = row[DonationRequestsTable.quantityNeeded],
            confirmedDonors = row[DonationRequestsTable.confirmedDonors],
            locationLat = row[DonationRequestsTable.locationLat],
            locationLng = row[DonationRequestsTable.locationLng],
            description = row[DonationRequestsTable.description],
            status = row[DonationRequestsTable.status]
        )
    }
}