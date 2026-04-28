package com.donai.api.di

import com.donai.api.application.request.CreateRequestUseCase
import com.donai.api.domain.request.RequestRepository
import com.donai.api.infrastructure.request.InMemoryRequestRepository
import org.koin.dsl.module

val appModule = module {

    single<RequestRepository> { InMemoryRequestRepository() }

    single { CreateRequestUseCase(get()) }
}