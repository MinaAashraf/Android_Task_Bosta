package com.bosta.androidtask_bosta.domain.useCases.profile

import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.repository.ProfileRepository
import com.bosta.androidtask_bosta.domain.utils.Result
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun execute(userId: Int): Result<User> = profileRepository.getUser(userId)
}