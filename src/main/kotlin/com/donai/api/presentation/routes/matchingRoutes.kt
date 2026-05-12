package com.donai.api.presentation.routes

import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.presentation.dto.matching.toResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.matchingRoutes(
    findMatchingDonorsUseCase: FindMatchingDonorsUseCase
) {

    route("/requests/{id}/matches") {

        get {

            val requestId = call.parameters["id"]
                ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing request id"
                )

            val radiusMeters =
                call.request.queryParameters["radius"]
                    ?.toDoubleOrNull()

            val result = findMatchingDonorsUseCase(
                requestId = requestId,
                radiusMeters = radiusMeters
            )

            call.respond(
                result.map { it.toResponse() }
            )
        }
    }
}