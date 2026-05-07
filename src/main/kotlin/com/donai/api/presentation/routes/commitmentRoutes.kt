package com.donai.api.presentation.routes

import com.donai.api.application.commitment.ConfirmCommitmentUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch
import io.ktor.server.routing.route

fun Route.commitmentRoutes(
    confirmCommitmentUseCase: ConfirmCommitmentUseCase
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
    }
}