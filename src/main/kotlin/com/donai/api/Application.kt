package com.donai.api

import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.application.request.GetRequestsUseCase
import com.donai.api.di.appModule
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.ktor.plugin.Koin
import com.donai.api.presentation.routes.requestRoutes
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlinx.serialization.json.Json
import com.donai.api.infrastructure.db.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {

    DatabaseFactory.init()

    install(Koin) {
        modules(appModule)
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    routing {
        val createRequestUseCase by inject<CreateRequestUseCase>()
        val getRequestsUseCase by inject<GetRequestsUseCase>()

        requestRoutes(createRequestUseCase, getRequestsUseCase)
    }
}