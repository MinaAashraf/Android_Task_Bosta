package com.bosta.androidtask_bosta.presentation.profile

import androidx.recyclerview.widget.DiffUtil
import com.bosta.androidtask_bosta.domain.model.Album

class AlbumDiffUtil : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
        newItem.albumId == oldItem.albumId

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
        oldItem == newItem
}