package com.bosta.androidtask_bosta.presentation

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.ActivityMainBinding
import com.bosta.androidtask_bosta.presentation.utils.ConnectivityReceiver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    lateinit var binding: ActivityMainBinding

    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        sharedViewModel.setInternetConnectivity(isConnected)
        if (!isConnected)
            Snackbar.make(binding.root, getString(R.string.offline_message), Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

}