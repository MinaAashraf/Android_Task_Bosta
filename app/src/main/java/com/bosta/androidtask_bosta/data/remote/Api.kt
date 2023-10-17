package com.bosta.androidtask_bosta.data.remote

import com.bosta.androidtask_bosta.domain.model.Album
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<User>

    @GET("albums")
    suspend fun getUserAlbums(@Query("userId") userId: Int): Response<List<Album>>

    @GET ("photos")
    suspend fun getAlbumPhotos (@Query ("albumId") albumId : Int) : Response<List<AlbumPhoto>>

    @GET("photos")
    suspend fun getPhotoById (@Query ("id") photoId : Int) : Response<List<AlbumPhoto>>

}