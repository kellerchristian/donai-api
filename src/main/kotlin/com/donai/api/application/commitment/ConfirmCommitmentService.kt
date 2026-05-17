package com.donai.api.application.commitment

import com.donai.api.application.flow.OnCommitmentConfirmedFlow

class ConfirmCommitmentService(
    private val confirmCommitmentUseCase: ConfirmCommitmentUseCase,
    private val onCommitmentConfirmedFlow: OnCommitmentConfirmedFlow
) {

    operator fun invoke(
        commitmentId: String
    ) {

        confirmCommitmentUseCase(
            commitmentId = commitmentId
        )

        onCommitmentConfirmedFlow(
            commitmentId = commitmentId
        )
    }
}