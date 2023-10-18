package com.bosta.androidtask_bosta.presentation.photo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.databinding.FragmentPhotoBinding
import com.bosta.androidtask_bosta.presentation.MainActivity
import com.bosta.androidtask_bosta.presentation.SharedViewModel
import com.bosta.androidtask_bosta.presentation.utils.checkPermission
import com.bosta.androidtask_bosta.presentation.utils.hide
import com.bosta.androidtask_bosta.presentation.utils.requestPermission
import com.bosta.androidtask_bosta.presentation.utils.show
import com.jsibbold.zoomage.ZoomageView
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private val binding by lazy { FragmentPhotoBinding.inflate(layoutInflater) }

    private val photoViewModel: PhotoViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val STORAGE_PERMISSION_CODE = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.see_photo_title)
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

    private fun handleConnectivityChange(isConnected: Boolean) {
        photoViewModel.handleConnectivityChange(isConnected)

    }

    private fun setUpShareBtn() {
        binding.shareBtn.setOnClickListener {
            if (checkStoragePermissions())
                shareImage(binding.photoView)
            else
                requestStoragePermission()
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

    // storage access permission handling:
    private fun checkStoragePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            checkPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private val storageActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                shareImage(binding.photoView)
            }
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent().apply {
                    action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    data = uri
                }
                storageActivityResultLauncher.launch(intent)
            } catch (e: Exception) {
                val intent = Intent().apply {
                    action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                }
                storageActivityResultLauncher.launch(intent)
            }
        } else {
            requestPermission(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareImage(binding.photoView)
                }
            }
        }
    }

}