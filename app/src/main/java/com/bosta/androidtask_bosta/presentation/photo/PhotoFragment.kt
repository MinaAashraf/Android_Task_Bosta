package com.bosta.androidtask_bosta.presentation.photo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.FragmentPhotoBinding
import com.bosta.androidtask_bosta.presentation.album.AlbumFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private val binding by lazy { FragmentPhotoBinding.inflate(layoutInflater) }

    private val navArgs: PhotoFragmentArgs by navArgs()

    private val photoViewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoViewModel.getPhotoById(navArgs.photoId)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        Log.d("navArgs", navArgs.photoId.toString())

    }

    private fun observeLiveData() {
        photoViewModel.photoLiveData.observe(viewLifecycleOwner) {
            binding.photo = it
        }
    }



}