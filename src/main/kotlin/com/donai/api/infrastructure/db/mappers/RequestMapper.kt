package com.donai.api.infrastructure.db.mappers

import com.donai.api.domain.request.DonationRequest
import com.donai.api.domain.request.RequestStatus
import com.donai.api.domain.shared.BloodGroup
import com.donai.api.domain.shared.RhFactor
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import org.jetbrains.exposed.sql.ResultRow

object RequestMapper {

    fun fromRow(row: ResultRow): DonationRequest {
        return DonationRequest(
            id = row[DonationRequestsTable.id],
            requesterId = row[DonationRequestsTable.requesterId],
            requiredBloodGroup = BloodGroup.valueOf(row[DonationRequestsTable.requiredBloodGroup]),
            requiredRhFactor = RhFactor.valueOf(row[DonationRequestsTable.requiredRhFactor]),
            quantityNeeded = row[DonationRequestsTable.quantityNeeded],
            confirmedDonors = row[DonationRequestsTable.confirmedDonors],
            locationLat = row[DonationRequestsTable.locationLat],
            locationLng = row[DonationRequestsTable.locationLng],
            description = row[DonationRequestsTable.description],
            status = RequestStatus.entries
                .find { it.name == row[DonationRequestsTable.status] }
                ?: throw IllegalStateException("Unknown status: ${row[DonationRequestsTable.status]}")
        )
    }
}