package com.donai.api.presentation.routes

import com.donai.api.application.user.CreateUserUseCase
import com.donai.api.application.user.GetUserProfileUseCase
import com.donai.api.application.user.UpdateDonationAvailabilityUseCase
import com.donai.api.presentation.dto.user.CreateUserRequest
import com.donai.api.presentation.dto.user.UpdateAvailabilityRequest
import com.donai.api.presentation.dto.user.toResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post

fun Route.userRoutes(
    createUserUseCase: CreateUserUseCase,
    getUserProfileUseCase: GetUserProfileUseCase,
    updateDonationAvailabilityUseCase: UpdateDonationAvailabilityUseCase
) {

    post("/users") {

        val request = call.receive<CreateUserRequest>()

        val user = createUserUseCase(
            firebaseUid = request.firebaseUid,

            name = request.name,

            email = request.email,

            bloodGroup = request.bloodGroup,

            rhFactor = request.rhFactor,

            latitude = request.latitude,

            longitude = request.longitude,

            locationDisplay = request.locationDisplay,

            gdprAccepted = request.gdprAccepted
        )

        call.respond(
            HttpStatusCode.Created,
            user.toResponse()
        )
    }

    get("/users/{id}") {

        val userId = call.parameters["id"]
            ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Missing user id"
            )

        val user = getUserProfileUseCase(userId)

        call.respond(
            user.toResponse()
        )
    }

    patch("/users/{id}/availability") {

        val userId = call.parameters["id"]
            ?: return@patch call.respond(
                HttpStatusCode.BadRequest,
                "Missing user id"
            )

        val request = call.receive<UpdateAvailabilityRequest>()

        try {
            updateDonationAvailabilityUseCase(
                userId = userId,
                availableToDonate = request.availableToDonate
            )
        }
        catch (e: IllegalArgumentException) {
            call.respond(HttpStatusCode.NotFound, e.message ?: "Not found")
        }

        call.respond(
            HttpStatusCode.OK
        )
    }
}