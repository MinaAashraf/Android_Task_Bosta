package com.bosta.androidtask_bosta.presentation.album

import android.R
import android.content.Context
import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bosta.androidtask_bosta.databinding.ItemPhotoBinding
import com.bosta.androidtask_bosta.domain.model.AlbumPhoto
import com.bosta.androidtask_bosta.presentation.utils.setUrl
import com.jsibbold.zoomage.ZoomageView


public class PhotosAdapter(
    context: Context,
    private val photos: List<AlbumPhoto>,
) :
    ArrayAdapter<AlbumPhoto>(context, 0, photos) {


    lateinit var binding: ItemPhotoBinding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var myView = convertView

        if (myView == null) {
            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            myView =
                inflater.inflate(com.bosta.androidtask_bosta.R.layout.item_photo, parent, false)
        }
        myView!!.findViewById<ImageView>(com.bosta.androidtask_bosta.R.id.image_view)
            .setUrl(photos[position].url)

        return myView

    }

}