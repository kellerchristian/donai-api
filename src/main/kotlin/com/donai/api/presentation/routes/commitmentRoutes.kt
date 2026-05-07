package com.donai.api.presentation.routes

import com.donai.api.application.commitment.CancelCommitmentUseCase
import com.donai.api.application.commitment.ConfirmCommitmentUseCase
import com.donai.api.application.commitment.SubmitAptitudeUseCase
import com.donai.api.dto.commitment.AptitudeResultResponse
import com.donai.api.dto.commitment.SubmitAptitudeRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch
import io.ktor.server.routing.route

fun Route.commitmentRoutes(
    confirmCommitmentUseCase: ConfirmCommitmentUseCase,
    submitAptitudeUseCase: SubmitAptitudeUseCase,
    cancelCommitmentUseCase: CancelCommitmentUseCase
) {

    route("/commitments") {

        patch("/{id}/confirm") {

            val id = call.parameters["id"]
                ?: return@patch call.respond(HttpStatusCode.BadRequest)

            try {
                confirmCommitmentUseCase.execute(id)
                call.respond(HttpStatusCode.NoContent)

            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "Not found")

            } catch (e: IllegalStateException) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Invalid state")
            }
        }

        patch("/{id}/aptitude") {

            val id = call.parameters["id"]
                ?: return@patch call.respond(HttpStatusCode.BadRequest)

            val body = call.receive<SubmitAptitudeRequest>()

            try {

                val result = submitAptitudeUseCase.execute(
                    commitmentId = id,
                    responses = body.responses
                )

                call.respond(
                    HttpStatusCode.OK,
                    AptitudeResultResponse(
                        status = result.name
                    )
                )

            } catch (e: IllegalArgumentException) {

                call.respond(
                    HttpStatusCode.NotFound,
                    e.message ?: "Not found"
                )

            } catch (e: IllegalStateException) {

                call.respond(
                    HttpStatusCode.Conflict,
                    e.message ?: "Invalid state"
                )
            }
        }

        patch("/{id}/cancel") {

            val id = call.parameters["id"]
                ?: return@patch call.respond(HttpStatusCode.BadRequest)

            try {

                cancelCommitmentUseCase.execute(id)

                call.respond(HttpStatusCode.NoContent)

            } catch (e: IllegalArgumentException) {

                call.respond(
                    HttpStatusCode.NotFound,
                    e.message ?: "Not found"
                )

            } catch (e: IllegalStateException) {

                call.respond(
                    HttpStatusCode.Conflict,
                    e.message ?: "Invalid state"
                )
            }
        }
    }
}