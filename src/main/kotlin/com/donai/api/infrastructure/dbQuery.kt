package com.donai.api.infrastructure

import org.jetbrains.exposed.sql.transactions.transaction

fun <T> dbQuery(block: () -> T): T =
    transaction { block() }