package com.bosta.androidtask_bosta.presentation.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.FragmentAlbumBinding
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumFragment : Fragment() {

    private val binding by lazy { FragmentAlbumBinding.inflate(layoutInflater) }

    private val albumViewModel: AlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumViewModel.getAlbumPhotos(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun setUpGridView(photos: List<AlbumPhoto>) {
        binding.gridView.adapter = PhotosAdapter(requireActivity(),photos)
    }

    private fun observeLiveData() {
        albumViewModel.albumPhotos.observe(viewLifecycleOwner) {
            it?.let {
                setUpGridView(it)
            }
        }
    }


}