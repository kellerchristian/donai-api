package com.donai.api.infrastructure.persistence.commitment

import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.domain.commitment.DonationCommitment
import com.donai.api.domain.commitment.CommitmentStatus
import com.donai.api.infrastructure.db.tables.DonationCommitmentsTable
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import com.donai.api.infrastructure.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import java.time.Instant
import java.time.LocalDateTime

class PostgresCommitmentRepository(
    private val mapper: CommitmentMapper
) : CommitmentRepository {

    override fun create(commitment: DonationCommitment): DonationCommitment = dbQuery {
        val now = Instant.now()
        DonationCommitmentsTable.insert {
            it[id] = commitment.id
            it[requestId] = commitment.requestId
            it[donorId] = commitment.donorId
            it[status] = commitment.status.name
            it[aptitudeResponses] = commitment.aptitudeResponses
            it[acceptedAt] = commitment.acceptedAt
            it[createdAt] = commitment.acceptedAt
            it[updatedAt] = commitment.acceptedAt
        }
        commitment
    }

    override fun findById(id: String): DonationCommitment? = dbQuery {
        DonationCommitmentsTable
            .select { DonationCommitmentsTable.id eq id }
            .map { mapper.fromRow(it) }
            .singleOrNull()
    }

    override fun findByRequestIdAndDonorId(
        requestId: String,
        donorId: String
    ): DonationCommitment? = dbQuery {

        DonationCommitmentsTable
            .select {
                (DonationCommitmentsTable.requestId eq requestId) and
                        (DonationCommitmentsTable.donorId eq donorId)
            }
            .map { mapper.fromRow(it) }
            .singleOrNull()
    }

    override fun countConfirmedByRequestId(requestId: String): Int = dbQuery {
        DonationCommitmentsTable
            .select {
                (DonationCommitmentsTable.requestId eq requestId) and
                        (DonationCommitmentsTable.status eq CommitmentStatus.CONFIRMED.name)
            }
            .count().toInt()
    }

    override fun updateStatus(
        id: String,
        status: CommitmentStatus,
        confirmedAt: Instant?
    ) = dbQuery {

        val now = Instant.now()

        DonationCommitmentsTable.update({ DonationCommitmentsTable.id eq id }) {
            it[this.status] = status.name
            it[this.confirmedAt] = confirmedAt
            it[this.updatedAt] = now
        }
        Unit
    }

    override fun updateAptitude(
        id: String,
        status: CommitmentStatus,
        aptitudeResponses: String
    ) = dbQuery {

        val now = Instant.now()

        val updatedRows = DonationCommitmentsTable.update({
            DonationCommitmentsTable.id eq id
        }) {

            it[this.status] = status.name
            it[this.aptitudeResponses] = aptitudeResponses
            it[this.updatedAt] = now
        }

        if (updatedRows == 0) {
            throw IllegalArgumentException("Commitment not found")
        }

        Unit
    }
}