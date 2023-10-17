package com.bosta.androidtask_bosta.presentation.photo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class PhotoViewModel @Inject constructor(private val getPhotoByIdUseCase: GetPhotoByIdUseCase) :
    ViewModel() {

    private val _photoLiveData = MutableLiveData<AlbumPhoto>()
    var photoLiveData: LiveData<AlbumPhoto> = _photoLiveData

    fun getPhotoById(photoId: Int) {
        viewModelScope.launch {
            getPhotoByIdUseCase.execute(photoId).onSuccess {
                _photoLiveData.value = it
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }

        }
    }


}