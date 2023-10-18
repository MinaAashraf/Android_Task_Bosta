package com.bosta.androidtask_bosta.presentation.photo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.useCases.profile.GetPhotoByIdUseCase
import com.bosta.androidtask_bosta.domain.utils.onFailure
import com.bosta.androidtask_bosta.domain.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    state: SavedStateHandle
) :
    ViewModel() {

    private val _photoLiveData = MutableLiveData<AlbumPhoto>()
    var photoLiveData: LiveData<AlbumPhoto> = _photoLiveData

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private val photoId = state.get<Int>("photoId")

    init {
        getPhotoById(photoId!!)
        _loading.value = true
    }

    private fun getPhotoById(photoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPhotoByIdUseCase.execute(photoId).onSuccess {
                _photoLiveData.postValue(it)
                _loading.postValue(false)
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }

        }
    }

    fun handleConnectivityChange(isConnected: Boolean) {
        if (isConnected && loading.value == true) {
            getPhotoById(photoId!!)
        }
    }

}