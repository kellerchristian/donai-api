package com.donai.api.presentation.routes

import com.donai.api.application.event.GetUserDonationHistoryUseCase
import com.donai.api.application.event.RegisterDonationEventUseCase
import com.donai.api.presentation.dto.event.RegisterDonationEventRequest
import com.donai.api.presentation.dto.event.toResponse
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.eventRoutes(
    registerDonationEventUseCase: RegisterDonationEventUseCase
) {

    route("/events") {

        post {

            val body =
                call.receive<RegisterDonationEventRequest>()

            val result = registerDonationEventUseCase(
                donorId = body.donorId,
                requestId = body.requestId,
                commitmentId = body.commitmentId
            )

            call.respond(result.toResponse())
        }
    }
}