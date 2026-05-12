package com.donai.api.infrastructure.db

import org.jetbrains.exposed.sql.transactions.transaction

fun <T> dbQuery(block: () -> T): T =
    transaction { block() }