package com.bosta.androidtask_bosta.presentation.album

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
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
class AlbumViewModel @Inject constructor(
    private val getAlbumPhotosUseCase: GetAlbumPhotosUseCase,
    state: SavedStateHandle
) :
    ViewModel() {


    private val _albumPhotos = MutableLiveData<List<AlbumPhoto>>()

    private val _searchAlbumPhotos = MutableLiveData<List<AlbumPhoto>>()

    private val _loading = MutableLiveData<Boolean>()
    var loading: LiveData<Boolean> = _loading

    private val userId = state.get<Int>("albumId")

    init {
        getAlbumPhotos(userId!!)
        _loading.value = true
    }

    val photosMediatorLiveData = MediatorLiveData<List<AlbumPhoto>>().apply {
        addSource(_albumPhotos) { updatedValue ->
            run {
                value = updatedValue
                _loading.value = false
            }
        }
        addSource(_searchAlbumPhotos) { updatedValue -> value = updatedValue }
    }

    private fun getAlbumPhotos(albumId: Int) {
        viewModelScope.launch {
            getAlbumPhotosUseCase.execute(albumId).onSuccess {
                _albumPhotos.value = it
            }.onFailure {
                Log.d("api exception: ", it.message.toString())
            }
        }

    }


    fun searchPhotos(searchQuery: String) {
        val resultPhotos = _albumPhotos.value?.let {
            it.filter { it.title?.lowercase()?.contains(searchQuery.lowercase()) ?: false }
        }
        resultPhotos?.let {
            _searchAlbumPhotos.value = it
        }
    }

    fun handleConnectivityChange(isConnected: Boolean) {
        if (isConnected && loading.value == true) {
            getAlbumPhotos(userId!!)
        }
    }

}