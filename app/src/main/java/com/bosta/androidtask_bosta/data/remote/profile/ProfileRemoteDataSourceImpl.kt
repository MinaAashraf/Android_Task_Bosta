package com.bosta.androidtask_bosta.data.remote.profile

import com.bosta.androidtask_bosta.data.exception.DataNotAvailableException
import com.bosta.androidtask_bosta.data.remote.Api
import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.utils.Result
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(val api: Api) : ProfileRemoteDataSource {
    override suspend fun getUser(userId: Int): Result<User> {
        return try {
            api.getUser(userId).body()?.let {
                Result.Success(it)
            } ?: kotlin.run { Result.Failure(DataNotAvailableException()) }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}