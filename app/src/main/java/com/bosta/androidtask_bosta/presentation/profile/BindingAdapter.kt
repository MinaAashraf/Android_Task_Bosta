package com.bosta.androidtask_bosta.presentation.profile

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bosta.androidtask_bosta.R
import com.bosta.androidtask_bosta.domain.model.UserAddress
import com.bosta.androidtask_bosta.presentation.utils.hide
import com.bosta.androidtask_bosta.presentation.utils.setUrl
import com.bosta.androidtask_bosta.presentation.utils.show
import com.jsibbold.zoomage.ZoomageView
import com.squareup.picasso.Picasso

@BindingAdapter("url")
fun bindImg(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.get().load(it).placeholder(R.drawable.place_holder).into(imageView)
    } ?: run { imageView.setImageResource(R.drawable.place_holder) }
}

