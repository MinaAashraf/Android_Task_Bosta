package com.bosta.androidtask_bosta.data.repository

import com.bosta.androidtask_bosta.data.remote.profile.ProfileRemoteDataSource
import com.bosta.androidtask_bosta.domain.model.Album
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.repository.ProfileRepository
import com.bosta.androidtask_bosta.domain.utils.Result
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val userRemoteDataSource: ProfileRemoteDataSource) :
    ProfileRepository {
    override suspend fun getUser(userId: Int): Result<User> = userRemoteDataSource.getUser(userId)
    override suspend fun getAlbums(userId: Int): Result<List<Album>> = userRemoteDataSource.getUserAlbums(userId)
    override suspend fun getAlbumPhotos(albumId: Int): Result<List<AlbumPhoto>> = userRemoteDataSource.getAlbumPhotos(albumId)
    override suspend fun getPhotoById(photoId: Int): Result<AlbumPhoto> = userRemoteDataSource.getPhotoById(photoId)
}