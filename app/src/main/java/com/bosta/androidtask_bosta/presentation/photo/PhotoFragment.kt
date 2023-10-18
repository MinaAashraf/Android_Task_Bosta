package com.bosta.androidtask_bosta.presentation.photo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bosta.androidtask_bosta.presentation.MainActivity
import com.bosta.androidtask_bosta.databinding.FragmentPhotoBinding
import com.bosta.androidtask_bosta.presentation.SharedViewModel
import com.bosta.androidtask_bosta.presentation.utils.hide
import com.bosta.androidtask_bosta.presentation.utils.show
import com.jsibbold.zoomage.ZoomageView
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private val binding by lazy { FragmentPhotoBinding.inflate(layoutInflater) }

    private val photoViewModel: PhotoViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = "See photo"
        observeLiveData()
        setUpShareBtn()
    }

    private fun observeLiveData() {
        photoViewModel.photoLiveData.observe(viewLifecycleOwner) {
            binding.photo = it
        }
        photoViewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (!it) {
                    binding.progressBar.hide()
                    binding.shareBtn.show()
                } else {
                    binding.progressBar.show()
                    binding.shareBtn.hide()
                }
            }
        }
        sharedViewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
            isConnected?.let {
                // reload data when being online
                handleConnectivityChange(it)
            }
        }

    }

    private fun setUpShareBtn() {
        binding.shareBtn.setOnClickListener {
            shareImage(binding.photoView)
        }
    }

    private fun shareImage(imageView: ZoomageView) {
        val intent = Intent(Intent.ACTION_SEND).setType("image/*")
        val bitmap = imageView.drawable.toBitmap() // your imageView here.
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap,
            "tempImage",
            null
        )
        val uri = Uri.parse(path)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, ""))
    }

    private fun handleConnectivityChange(isConnected: Boolean) {
        photoViewModel.handleConnectivityChange(isConnected)

    }


}