package com.donai.api.infrastructure.persistence.commitment

import com.donai.api.domain.commitment.CommitmentStatus
import com.donai.api.domain.commitment.DonationCommitment
import com.donai.api.infrastructure.db.tables.DonationCommitmentsTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.ZoneOffset

class CommitmentMapper {

    fun fromRow(row: ResultRow): DonationCommitment {
        return DonationCommitment(
            id = row[DonationCommitmentsTable.id],
            requestId = row[DonationCommitmentsTable.requestId],
            donorId = row[DonationCommitmentsTable.donorId],
            status = CommitmentStatus.entries
                .find { it.name == row[DonationCommitmentsTable.status] }
                ?: throw IllegalStateException("Unknown status: ${row[DonationCommitmentsTable.status]}"),
            aptitudeResponses = row[DonationCommitmentsTable.aptitudeResponses],
            acceptedAt = row[DonationCommitmentsTable.acceptedAt],
            confirmedAt = row[DonationCommitmentsTable.confirmedAt]
        )
    }
}