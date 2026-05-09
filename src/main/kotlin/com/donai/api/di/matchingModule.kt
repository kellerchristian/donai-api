package com.donai.api.di


import com.donai.api.application.matching.FindMatchingDonorsUseCase
import com.donai.api.domain.matching.DonorMatcherRepository
import com.donai.api.infrastructure.matching.MockDonorMatcherRepository
import org.koin.dsl.module

val matchingModule = module {

    single<DonorMatcherRepository> {
        MockDonorMatcherRepository()
    }

    single {
        FindMatchingDonorsUseCase(
            requestRepository = get(),
            donorMatcherRepository = get()
        )
    }
}