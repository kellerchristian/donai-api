package com.donai.api.infrastructure.db.repositories.user

import com.donai.api.domain.user.User
import com.donai.api.domain.user.UserRepository
import com.donai.api.infrastructure.db.mappers.toUser
import com.donai.api.infrastructure.db.tables.UsersTable
import com.donai.api.infrastructure.db.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.time.Instant

class PostgresUserRepository : UserRepository {

    override fun create(user: User): User = dbQuery {

        UsersTable.insert {
            it[id] = user.id
            it[firebaseUid] = user.firebaseUid
            it[name] = user.name
            it[email] = user.email

            it[bloodGroup] = user.bloodType.bloodGroup.name
            it[rhFactor] = user.bloodType.rhFactor.name

            it[locationLat] = user.location.latitude
            it[locationLng] = user.location.longitude
            it[locationDisplay] = user.locationDisplay

            it[availableToDonate] = user.availableToDonate

            it[lastDonationAt] = user.lastDonationAt
            it[nextEligibleAt] = user.nextEligibleAt

            it[gdprAccepted] = user.gdprAccepted
            it[gdprAcceptedAt] = user.gdprAcceptedAt

            it[createdAt] = user.createdAt
            it[updatedAt] = user.updatedAt
            it[deletedAt] = user.deletedAt
        }

        // opcional: re-leer desde DB para asegurar consistencia
        UsersTable
            .select { UsersTable.id eq user.id }
            .map { it.toUser() }
            .single()
    }

    override fun findById(id: String): User? = dbQuery {

        UsersTable
            .select {
                (UsersTable.id eq id) and UsersTable.deletedAt.isNull()
            }
            .map { it.toUser() }
            .singleOrNull()
    }

    override fun findByFirebaseUid(firebaseUid: String): User? = dbQuery {

        UsersTable
            .select {
                (UsersTable.firebaseUid eq firebaseUid) and
                        UsersTable.deletedAt.isNull()
            }
            .map { it.toUser() }
            .singleOrNull()
    }

    override fun updateAvailability(
        userId: String,
        availableToDonate: Boolean
    ) = dbQuery {

        val updatedRows = UsersTable.update({
            (UsersTable.id eq userId) and UsersTable.deletedAt.isNull()
        }) {
            it[this.availableToDonate] = availableToDonate
            it[updatedAt] = Instant.now()
        }

        updatedRows > 0
    }

    override fun findAllEligibleDonors(): List<User> = dbQuery {

        UsersTable
            .select {
                (UsersTable.availableToDonate eq true) and
                        UsersTable.deletedAt.isNull() and
                        (
                                UsersTable.nextEligibleAt.isNull() or
                                        (UsersTable.nextEligibleAt lessEq Instant.now())
                                )
            }
            .map { it.toUser() }
    }
}