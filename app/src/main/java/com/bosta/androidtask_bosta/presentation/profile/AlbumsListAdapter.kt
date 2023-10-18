package com.bosta.androidtask_bosta.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bosta.androidtask_bosta.databinding.ItemAlbumBinding
import com.bosta.androidtask_bosta.domain.model.Album

class AlbumsListAdapter(
    private val onItemClickListener: OnItemClickListener,
) :
    ListAdapter<Album, AlbumsListAdapter.MyViewHolder>(AlbumDiffUtil()) {


    class MyViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(album: Album) {
            binding.album = album
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlbumBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener {
            onItemClickListener.onAlbumItemClick(getItem(holder.adapterPosition).albumId!!)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    interface OnItemClickListener {
        fun onAlbumItemClick(albumId: Int)
    }

}