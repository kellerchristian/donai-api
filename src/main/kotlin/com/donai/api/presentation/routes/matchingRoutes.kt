package com.donai.api.presentation.routes

import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.dto.matching.toResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.matchingRoutes(
    findMatchingDonorsUseCase: FindMatchingDonorsUseCase
) {
    get("/requests/{id}/matches") {

        val requestId = call.parameters["id"]
            ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Missing request id"
            )

        val radius = call.request.queryParameters["radius"]
            ?.toDoubleOrNull()

        val matches = findMatchingDonorsUseCase(
            requestId,
            radius
        )

        call.respond(
            matches.map { it.toResponse() }
        )
    }
}