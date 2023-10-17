package com.bosta.androidtask_bosta.domain.useCases.profile

import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.domain.repository.ProfileRepository
import com.bosta.androidtask_bosta.domain.utils.Result
import javax.inject.Inject

class GetPhotoByIdUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun execute(photoId: Int): Result<AlbumPhoto> = profileRepository.getPhotoById(photoId)

}