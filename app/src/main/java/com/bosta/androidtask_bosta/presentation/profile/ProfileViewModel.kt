package com.bosta.androidtask_bosta.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    private val getUserAlbumsUseCase: GetUserAlbumsUseCase,
) : ViewModel() {

    private val _userData = MutableLiveData<User>()
    var userData: LiveData<User> = _userData

    private val _userAlbums = MutableLiveData<List<Album>>()
    var userAlbums: LiveData<List<Album>> = _userAlbums

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    var randomId = (1..10).random()

    init {
        getUser()
        getUserAlbums()
        _loading.value = true
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.execute(randomId).onSuccess {
                _userData.value = it
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }
        }
    }

    private fun getUserAlbums() {
        viewModelScope.launch {
            getUserAlbumsUseCase.execute(randomId).onSuccess {
                _userAlbums.value = it
                _loading.value = false
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }
        }
    }

    fun handleConnectivityChange (isConnected : Boolean){
        if (isConnected && loading.value == true) {
            getUser()
            getUserAlbums()
        }
    }


}