package com.donai.api.domain.commitment

object CommitmentStateMachine {

    private val transitions = mapOf(
        CommitmentStatus.PENDING_APTITUDE to setOf(
            CommitmentStatus.FIT,
            CommitmentStatus.UNFIT,
            CommitmentStatus.CANCELLED
        ),
        CommitmentStatus.FIT to setOf(
            CommitmentStatus.CONFIRMED,
            CommitmentStatus.CANCELLED
        ),
        CommitmentStatus.UNFIT to setOf(
            CommitmentStatus.CANCELLED
        ),
        CommitmentStatus.CONFIRMED to emptySet(),
        CommitmentStatus.CANCELLED to emptySet()
    )

    fun canTransition(from: CommitmentStatus, to: CommitmentStatus): Boolean {
        return transitions[from]?.contains(to) == true
    }

    fun requireTransition(from: CommitmentStatus, to: CommitmentStatus) {
        if (!canTransition(from, to)) {
            throw IllegalStateException("Invalid transition: $from → $to")
        }
    }
}