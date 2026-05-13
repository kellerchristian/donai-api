package com.donai.api.presentation.routes

import com.donai.api.application.commitment.CreateCommitmentUseCase
import com.donai.api.application.flow.HandleRequestCreatedFlow
import com.donai.api.application.request.CancelRequestUseCase
import com.donai.api.application.request.CreateRequestService
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import com.donai.api.application.request.GetRequestByIdUseCase
import com.donai.api.application.request.GetAllRequestsUseCase
import com.donai.api.application.request.GetFeedRequestsUseCase
import com.donai.api.presentation.dto.commitment.toResponse
import com.donai.api.presentation.dto.request.CreateRequestRequest
import com.donai.api.presentation.dto.request.toDetailResponse
import com.donai.api.presentation.dto.request.toResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call

fun Route.requestRoutes(
    getAllRequestsUseCase: GetAllRequestsUseCase,
    getFeedRequestsUseCase: GetFeedRequestsUseCase,
    getRequestByIdUseCase: GetRequestByIdUseCase,
    cancelRequestUseCase: CancelRequestUseCase,
    createCommitmentUseCase: CreateCommitmentUseCase,
    createRequestService: CreateRequestService
) {

    route("/requests") {
        post {
            val body = call.receive<CreateRequestRequest>()

            val userId = "mock-user-123"

            val result = createRequestService(
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
            val mode = call.request.queryParameters["mode"] ?: "feed"
            val userId = "mock-user-123"

            val result = when (mode) {

                "all" -> getAllRequestsUseCase()

                "feed" -> getFeedRequestsUseCase(userId)

                else -> getAllRequestsUseCase()
            }

            call.respond(result.map { it.toDetailResponse() })
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

        patch("/{id}/cancel") {

            val id = call.parameters["id"]
                ?: return@patch call.respond(HttpStatusCode.BadRequest)

            try {
                cancelRequestUseCase(id)
                call.respond(HttpStatusCode.NoContent)

            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "Not found")

            } catch (e: IllegalStateException) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Invalid state")
            }
        }

        post("/{id}/commitments") {

            val requestId = call.parameters["id"]
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Missing request id")

            val donorId = "mock-user-123"

            try {

                val commitment = createCommitmentUseCase.execute(
                    requestId = requestId,
                    donorId = donorId
                )

                call.respond(HttpStatusCode.Created, commitment.toResponse())

            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "Request not found")

            } catch (e: IllegalStateException) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Invalid state")
            }
        }
    }
}