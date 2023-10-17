package com.bosta.androidtask_bosta.presentation.album

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
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

    private val photos = mutableListOf<AlbumPhoto>()

    private val gridAdapter by lazy { PhotosAdapter(requireActivity(), photos) }

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
        setUpUi()
    }


    private fun setUpUi() {
        setUpGridView()
        setUpSearchView()
    }

    private fun setUpGridView() {
        binding.gridView.adapter = gridAdapter
    }

    private fun setUpSearchView() {
        binding.searchView.apply {
            clearFocus()
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let {
                        albumViewModel.searchPhotos(it)
                    }
                    return false
                }

            })
        }
    }

    private fun observeLiveData() {
        albumViewModel.photosMediatorLiveData.observe(viewLifecycleOwner) {
            it?.let {
                //  setUpGridView(it)
                photos.clear()
                photos.addAll(it)
                gridAdapter.notifyDataSetChanged()
            }
        }
    }

}