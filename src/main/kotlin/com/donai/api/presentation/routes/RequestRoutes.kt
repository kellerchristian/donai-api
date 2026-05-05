package com.donai.api.presentation.routes

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.application.request.GetRequestByIdUseCase
import com.donai.api.application.request.GetRequestsUseCase
import com.donai.api.dto.request.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call

fun Route.requestRoutes(
    createRequestUseCase: CreateRequestUseCase,
    getRequestsUseCase: GetRequestsUseCase,
    getRequestByIdUseCase: GetRequestByIdUseCase
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

        get {
            val requests = getRequestsUseCase()

            val response = requests.map { it.toListItemResponse() }

            call.respond(response)
        }

        get("/{id}") {

            val id = call.parameters["id"]

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing id")
                return@get
            }

            val request = getRequestByIdUseCase(id)

            if (request == null) {
                call.respond(HttpStatusCode.NotFound, "Request not found")
                return@get
            }

            call.respond(request.toDetailResponse())
        }
    }
}