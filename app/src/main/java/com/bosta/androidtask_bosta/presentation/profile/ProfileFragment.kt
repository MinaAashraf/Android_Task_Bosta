package com.bosta.androidtask_bosta.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(
            layoutInflater
        )
    }

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val randomId = (1..10).random()
        profileViewModel.getUser(randomId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserLiveData()
        binding.userNameTv.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_albumFragment) }
    }

    private fun observeUserLiveData (){
        profileViewModel.productDetailsLiveData.observe(viewLifecycleOwner) {
            it?.let { binding.user = it }
        }
    }


}