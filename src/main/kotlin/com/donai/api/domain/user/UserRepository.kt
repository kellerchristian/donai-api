package com.donai.api.domain.user

interface UserRepository {

    fun create(user: User): User

    fun findById(id: String): User?

    fun findByFirebaseUid(firebaseUid: String): User?

    fun updateAvailability(
        userId: String,
        availableToDonate: Boolean
    ): Boolean

    fun findAllEligibleDonors(): List<User>
}