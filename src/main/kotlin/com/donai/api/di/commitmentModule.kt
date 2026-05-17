package com.donai.api.di

import com.donai.api.application.commitment.CancelCommitmentUseCase
import com.donai.api.application.commitment.ConfirmCommitmentService
import com.donai.api.application.commitment.ConfirmCommitmentUseCase
import com.donai.api.application.commitment.CreateCommitmentUseCase
import com.donai.api.application.commitment.SubmitAptitudeUseCase
import com.donai.api.application.flow.OnCommitmentConfirmedFlow
import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.infrastructure.db.mappers.CommitmentMapper
import com.donai.api.infrastructure.db.repositories.commitment.PostgresCommitmentRepository
import org.koin.dsl.module

val commitmentModule = module {

    single {
        CommitmentMapper()
    }

    single<CommitmentRepository> {
        PostgresCommitmentRepository(get())
    }

    single {
        CreateCommitmentUseCase(
            requestRepository = get(),
            commitmentRepository = get()
        )
    }

    single {
        ConfirmCommitmentUseCase(
            commitmentRepository = get()
        )
    }

    single {
        OnCommitmentConfirmedFlow(
            commitmentRepository = get(),
            registerDonationEventUseCase = get(),
            recalculateUserEligibilityUseCase = get(),
            closeRequestIfCompletedUseCase = get()
        )
    }

    single {
        ConfirmCommitmentService(
            confirmCommitmentUseCase = get(),
            onCommitmentConfirmedFlow = get()
        )
    }

    single {
        SubmitAptitudeUseCase(get())
    }

    single {
        CancelCommitmentUseCase(get())
    }
}