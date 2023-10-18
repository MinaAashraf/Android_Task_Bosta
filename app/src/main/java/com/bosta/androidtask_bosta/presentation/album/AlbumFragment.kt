package com.bosta.androidtask_bosta.presentation.album

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bosta.androidtask_bosta.MainActivity
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.FragmentAlbumBinding
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.presentation.utils.hide
import com.bosta.androidtask_bosta.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumFragment : Fragment() {

    private val binding by lazy { FragmentAlbumBinding.inflate(layoutInflater) }

    private val albumViewModel: AlbumViewModel by viewModels()

    private val photos = mutableListOf<AlbumPhoto>()

    private val gridAdapter by lazy { PhotosAdapter(requireActivity(), photos) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.album_photos_title)
        observeLiveData()
        setUpUi()
    }


    private fun setUpUi() {
        setUpSearchView()
        setUpGridView()
    }

    private fun setUpGridView() {
        binding.gridView.adapter = gridAdapter
        binding.gridView.setOnItemClickListener { parent, view, position, id ->
            val photoId = photos[position].id!!
            findNavController().navigate(
                AlbumFragmentDirections.actionAlbumFragmentToPhotoFragment(photoId)
            )
        }

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
        albumViewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (!it)
                    binding.progressBar.hide()
                else
                    binding.progressBar.show()
            }
        }
    }


}