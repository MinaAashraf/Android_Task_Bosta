package com.bosta.androidtask_bosta.data.remote.profile

import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.utils.Result

interface ProfileRemoteDataSource {
    suspend fun getUser (userId : Int) : Result<User>
}