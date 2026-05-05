package com.donai.api.di

import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.application.request.GetRequestByIdUseCase
import com.donai.api.application.request.GetAllRequestsUseCase
import com.donai.api.application.request.GetFeedRequestsUseCase
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.persistence.request.PostgresRequestRepository
import org.koin.dsl.module

val appModule = module {

    single<RequestRepository> { PostgresRequestRepository() }

    single { CreateRequestUseCase(get()) }
    single { GetAllRequestsUseCase(get()) }
    single { GetFeedRequestsUseCase(get()) }
    single { GetRequestByIdUseCase(get()) }
}