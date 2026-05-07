package com.donai.api.di

import com.donai.api.application.commitment.CancelCommitmentUseCase
import com.donai.api.application.commitment.ConfirmCommitmentUseCase
import com.donai.api.application.commitment.CreateCommitmentUseCase
import com.donai.api.application.commitment.SubmitAptitudeUseCase
import org.koin.dsl.module
import com.donai.api.domain.commitment.CommitmentRepository
import com.donai.api.infrastructure.persistence.commitment.PostgresCommitmentRepository
import com.donai.api.infrastructure.persistence.commitment.CommitmentMapper

val commitmentModule = module {

    single { CommitmentMapper() }

    single<CommitmentRepository> {
        PostgresCommitmentRepository(get())
    }

    factory {
        CreateCommitmentUseCase(get(), get())
    }
    factory {
        ConfirmCommitmentUseCase(get(), get())
    }
    factory {
        SubmitAptitudeUseCase(get())
    }
    factory {
        CancelCommitmentUseCase(get())
    }
}