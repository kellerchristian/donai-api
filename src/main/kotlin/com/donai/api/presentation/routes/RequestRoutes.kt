package com.donai.api.presentation.routes

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.dto.request.*
import io.ktor.server.application.call

fun Route.requestRoutes(
    createRequestUseCase: CreateRequestUseCase
) {

    route("/requests") {
        post {
            val body = call.receive<CreateRequestRequest>()

            val userId = "mock-user-123"

            val result = createRequestUseCase(
                requesterId = userId,
                bloodGroup = body.bloodGroup,
                rhFactor = body.rhFactor,
                quantity = body.quantity,
                lat = body.lat,
                lng = body.lng,
                description = body.description
            )

            call.respond(result.toResponse())
        }
    }
}