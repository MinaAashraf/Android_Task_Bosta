package com.bosta.androidtask_bosta.domain.useCases.profile

import com.bosta.androidtask_bosta.domain.model.Album
import com.bosta.androidtask_bosta.domain.repository.ProfileRepository
import com.bosta.androidtask_bosta.domain.utils.Result
import javax.inject.Inject

class GetUserAlbumsUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun execute(userId: Int): Result<List<Album>> = profileRepository.getAlbums(userId)
}