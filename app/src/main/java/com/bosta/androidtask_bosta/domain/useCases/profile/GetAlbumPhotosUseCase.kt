package com.bosta.androidtask_bosta.domain.useCases.profile

import com.bosta.androidtask_bosta.domain.model.Album
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.domain.repository.ProfileRepository
import com.bosta.androidtask_bosta.domain.utils.Result
import javax.inject.Inject

class GetAlbumPhotosUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun execute(albumId: Int): Result<List<AlbumPhoto>> = profileRepository.getAlbumPhotos(albumId)
}