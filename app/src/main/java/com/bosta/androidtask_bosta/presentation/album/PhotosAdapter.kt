package com.bosta.androidtask_bosta.presentation.album

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bosta.androidtask_bosta.databinding.ItemAlbumBinding
import com.bosta.androidtask_bosta.databinding.ItemPhotoBinding
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto

public class PhotosAdapter(context: Context, photos: List<AlbumPhoto>) :
    ArrayAdapter<AlbumPhoto>(context, 0, photos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPhotoBinding.inflate(inflater, parent, false)
            binding.photo = getItem(position)
            return binding.root
        }
        return convertView

    }


}