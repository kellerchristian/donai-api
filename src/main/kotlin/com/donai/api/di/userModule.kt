package com.donai.api.di

import com.donai.api.application.event.GetUserDonationHistoryUseCase
import com.donai.api.application.user.CreateUserUseCase
import com.donai.api.application.user.GetUserProfileUseCase
import com.donai.api.application.user.RecalculateUserEligibilityUseCase
import com.donai.api.application.user.UpdateDonationAvailabilityUseCase
import com.donai.api.domain.user.UserRepository
import com.donai.api.infrastructure.db.repositories.user.PostgresUserRepository
import org.koin.dsl.module

val userModule = module {

    single<UserRepository> {
        PostgresUserRepository()
    }

    single {
        CreateUserUseCase(
            userRepository = get()
        )
    }

    single {
        GetUserProfileUseCase(
            userRepository = get()
        )
    }

    single {
        GetUserDonationHistoryUseCase(get())
    }

    single {
        UpdateDonationAvailabilityUseCase(
            userRepository = get()
        )
    }

    single {
        RecalculateUserEligibilityUseCase(
            userRepository = get(),
            donorEligibilityPolicy = get()
        )
    }
}