package com.donai.api.infrastructure.db

import com.donai.api.infrastructure.db.tables.DonationCommitmentsTable
import com.donai.api.infrastructure.db.tables.DonationRequestsTable
import com.donai.api.infrastructure.db.tables.UsersTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5433/donai"
            driverClassName = "org.postgresql.Driver"
            username = System.getenv("DB_USER") ?: "postgres"
            password = System.getenv("DB_PASSWORD") ?: "postgres"
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        val database = Database.connect(dataSource)

        transaction(database) {
            SchemaUtils.create(
                UsersTable,
                DonationRequestsTable,
                DonationCommitmentsTable
            )
        }
    }
}