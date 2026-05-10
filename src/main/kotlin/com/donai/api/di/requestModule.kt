package com.donai.api.di

import com.donai.api.application.request.CancelRequestUseCase
import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.application.request.GetRequestByIdUseCase
import com.donai.api.application.request.GetAllRequestsUseCase
import com.donai.api.application.request.GetFeedRequestsUseCase
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.db.repositories.request.PostgresRequestRepository
import org.koin.dsl.module

val requestModule = module {

    single<RequestRepository> { PostgresRequestRepository() }

    factory { CreateRequestUseCase(get()) }
    factory { GetAllRequestsUseCase(get()) }
    factory { GetFeedRequestsUseCase(get()) }
    factory { GetRequestByIdUseCase(get()) }
    factory { CancelRequestUseCase(get()) }
}