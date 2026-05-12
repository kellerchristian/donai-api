package com.donai.api.application.user

import com.donai.api.domain.user.User
import com.donai.api.domain.user.UserRepository

class GetUserProfileUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        userId: String
    ): User {
        return userRepository.findById(userId)
            ?: throw IllegalArgumentException(
                "User not found"
            )
    }
}