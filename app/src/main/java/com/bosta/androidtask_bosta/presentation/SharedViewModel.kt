package com.bosta.androidtask_bosta.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private var _isConnected = MutableLiveData<Boolean>(false)
    var isConnected: LiveData<Boolean> = _isConnected

    fun setInternetConnectivity(isConnected: Boolean) {
        _isConnected.value = isConnected
    }

}