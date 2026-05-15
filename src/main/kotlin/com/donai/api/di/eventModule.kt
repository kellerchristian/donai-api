package com.donai.api.di

import com.donai.api.application.event.GetUserDonationHistoryUseCase
import com.donai.api.application.event.RegisterDonationEventUseCase
import com.donai.api.domain.event.DonationEventRepository
import com.donai.api.infrastructure.db.repositories.event.PostgresDonationEventRepository
import org.koin.dsl.module

val eventModule = module {

    single<DonationEventRepository> {
        PostgresDonationEventRepository()
    }

    factory {
        RegisterDonationEventUseCase(get())
    }

    factory {
        GetUserDonationHistoryUseCase(get())
    }
}