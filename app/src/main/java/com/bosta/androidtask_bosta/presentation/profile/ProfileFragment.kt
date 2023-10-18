package com.bosta.androidtask_bosta.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bosta.androidtask_bosta.MainActivity
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.FragmentProfileBinding
import com.bosta.androidtask_bosta.presentation.utils.hide
import com.bosta.androidtask_bosta.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ProfileFragment : Fragment(), AlbumsListAdapter.OnItemClickListener {

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(
            layoutInflater
        )
    }

    private val profileViewModel: ProfileViewModel by viewModels ()

    private val albumsAdapter: AlbumsListAdapter by lazy { AlbumsListAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.profile_title)
        observeLiveData()
        setUpAlbumsRecyclerView()
    }


    private fun setUpAlbumsRecyclerView() {
        binding.albumsRecyclerView.adapter = albumsAdapter
    }

    private fun observeLiveData() {
        profileViewModel.userData.observe(viewLifecycleOwner) {
            it?.let {
                binding.user = it
            }
        }
        profileViewModel.userAlbums.observe(viewLifecycleOwner) {
            it?.let {
                albumsAdapter.submitList(it)
            }
        }

        profileViewModel.loading.observe(viewLifecycleOwner){
               it?.let {
                   if (!it)
                       binding.progressBar.hide()
                   else
                       binding.progressBar.show()

               }
        }

    }

    override fun onAlbumItemClick(albumId: Int) {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToAlbumFragment(albumId)
        )
    }
}


