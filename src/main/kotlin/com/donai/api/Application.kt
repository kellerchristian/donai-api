package com.donai.api

import com.donai.api.application.commitment.CancelCommitmentUseCase
import com.donai.api.application.commitment.ConfirmCommitmentUseCase
import com.donai.api.application.commitment.CreateCommitmentUseCase
import com.donai.api.application.commitment.SubmitAptitudeUseCase
import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.application.request.CancelRequestUseCase
import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.application.request.GetRequestByIdUseCase
import com.donai.api.application.request.GetAllRequestsUseCase
import com.donai.api.application.request.GetFeedRequestsUseCase
import com.donai.api.application.user.CreateUserUseCase
import com.donai.api.application.user.GetUserProfileUseCase
import com.donai.api.application.user.UpdateDonationAvailabilityUseCase
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
import com.donai.api.presentation.routes.commitmentRoutes
import com.donai.api.presentation.routes.matchingRoutes
import com.donai.api.presentation.routes.userRoutes
import io.ktor.server.engine.*
import kotlinx.serialization.modules.SerializersModule
import java.time.Instant

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
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(Instant::class, InstantSerializer)
            }
        })
    }

    routing {
        val createRequestUseCase by inject<CreateRequestUseCase>()
        val getAllRequestsUseCase by inject<GetAllRequestsUseCase>()
        val getFeedRequestsUseCase by inject<GetFeedRequestsUseCase>()
        val getRequestByIdUseCase by inject<GetRequestByIdUseCase>()
        val cancelRequestUseCase by inject<CancelRequestUseCase>()
        val createCommitmentUseCase by inject<CreateCommitmentUseCase>()
        val confirmCommitmentUseCase by inject<ConfirmCommitmentUseCase>()
        val submitAptitudeUseCase by inject<SubmitAptitudeUseCase>()
        val cancelCommitmentUseCase by inject<CancelCommitmentUseCase>()
        val findMatchingDonorsUseCase by inject<FindMatchingDonorsUseCase>()
        val createUserUseCase by inject<CreateUserUseCase>()
        val getUserProfileUseCase by inject<GetUserProfileUseCase>()
        val updateDonationAvailabilityUseCase by inject< UpdateDonationAvailabilityUseCase>()


        requestRoutes(
            createRequestUseCase,
            getAllRequestsUseCase,
            getFeedRequestsUseCase,
            getRequestByIdUseCase,
            cancelRequestUseCase,
            createCommitmentUseCase
        )
        commitmentRoutes(
            confirmCommitmentUseCase,
            submitAptitudeUseCase,
            cancelCommitmentUseCase
        )
        matchingRoutes(
            findMatchingDonorsUseCase
        )
        userRoutes(
            createUserUseCase,
            getUserProfileUseCase,
            updateDonationAvailabilityUseCase
        )
    }
}