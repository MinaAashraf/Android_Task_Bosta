package com.bosta.androidtask_bosta.presentation.album

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosta.androidtask_bosta.domain.model.Album
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.domain.model.User
import com.bosta.androidtask_bosta.domain.useCases.profile.GetAlbumPhotosUseCase
import com.bosta.androidtask_bosta.domain.utils.onFailure
import com.bosta.androidtask_bosta.domain.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AlbumViewModel @Inject constructor(private val getAlbumPhotosUseCase: GetAlbumPhotosUseCase) :
    ViewModel() {

    private val _albumPhotos = MutableLiveData<List<AlbumPhoto>>()
    var albumPhotos: LiveData<List<AlbumPhoto>> = _albumPhotos


    fun getAlbumPhotos(albumId: Int) {
        viewModelScope.launch {
            getAlbumPhotosUseCase.execute(albumId).onSuccess {
                _albumPhotos.value = it
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }
        }

    }

}