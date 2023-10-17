package com.bosta.androidtask_bosta.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosta.androidtask_bosta.domain.model.Album
import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.useCases.profile.GetUserAlbumsUseCase
import com.bosta.androidtask_bosta.domain.useCases.profile.GetUserUseCase
import com.bosta.androidtask_bosta.domain.utils.onFailure
import com.bosta.androidtask_bosta.domain.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserAlbumsUseCase: GetUserAlbumsUseCase
) :
    ViewModel() {

    private val _userData = MutableLiveData<User>()
    var userData: LiveData<User> = _userData

    private val _userAlbums = MutableLiveData<List<Album>>()
    var userAlbums: LiveData<List<Album>> = _userAlbums


    fun getUser(userId: Int) {
        viewModelScope.launch {
            getUserUseCase.execute(userId).onSuccess {
                _userData.value = it
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }
        }
    }

    fun getUserAlbums(userId: Int) {
        viewModelScope.launch {
            getUserAlbumsUseCase.execute(userId).onSuccess {
                _userAlbums.value = it
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }
        }
    }


}