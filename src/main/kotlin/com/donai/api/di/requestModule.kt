package com.donai.api.di

import com.donai.api.application.request.CancelRequestUseCase
import com.donai.api.application.request.CloseRequestIfCompletedUseCase
import com.donai.api.application.request.CreateRequestService
import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.application.request.GetRequestByIdUseCase
import com.donai.api.application.request.GetAllRequestsUseCase
import com.donai.api.application.request.GetFeedRequestsUseCase
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.db.repositories.request.PostgresRequestRepository
import org.koin.dsl.module

val requestModule = module {

    single<RequestRepository> {
        PostgresRequestRepository()
    }

    single {
        CreateRequestUseCase(get())
    }

    single {
        GetAllRequestsUseCase(get())
    }

    single {
        GetFeedRequestsUseCase(get(), get(), get())
    }

    single {
        GetRequestByIdUseCase(get())
    }

    single {
        CancelRequestUseCase(get())
    }

    single {
        CloseRequestIfCompletedUseCase(
            requestRepository = get(),
            commitmentRepository = get()
        )
    }

    single {
        CreateRequestService(
            createRequestUseCase = get(),
            handleRequestCreatedFlow = get()
        )
    }
}