package com.donai.api.di

import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.domain.matching.BloodCompatibilityPolicy
import com.donai.api.domain.matching.DonorAvailabilityPolicy
import com.donai.api.domain.matching.DonorMatchingEngine
import com.donai.api.domain.matching.DonorRepository
import com.donai.api.infrastructure.db.repositories.matching.PostgresDonorRepository
import com.donai.api.application.flow.HandleRequestCreatedFlow
import com.donai.api.application.notification.NotifyMatchingDonorsUseCase
import org.koin.dsl.module

val matchingModule = module {

    single {
        BloodCompatibilityPolicy()
    }

    single {
        DonorAvailabilityPolicy()
    }

    single {
        DonorMatchingEngine(
            bloodCompatibilityPolicy = get(),
            donorAvailabilityPolicy = get()
        )
    }

    single<DonorRepository> {
        PostgresDonorRepository()
    }

    single {
        FindMatchingDonorsUseCase(
            requestRepository = get(),
            donorRepository = get(),
            donorMatchingEngine = get()
        )
    }
    single {
        NotifyMatchingDonorsUseCase(
            requestRepository = get()
        )
    }

    single {
        HandleRequestCreatedFlow(
            findMatchingDonorsUseCase = get(),
            notifyMatchingDonorsUseCase = get()
        )
    }
}