package com.bosta.androidtask_bosta.data.remote

import com.bosta.androidtask_bosta.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<User>


}