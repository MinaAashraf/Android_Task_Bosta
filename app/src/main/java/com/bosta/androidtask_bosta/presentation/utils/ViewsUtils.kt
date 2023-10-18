package com.bosta.androidtask_bosta.presentation.utils

import android.view.View
import android.widget.ImageView
import com.bosta.androidtask_bosta.R
import com.squareup.picasso.Picasso


fun View.show() {
    if (visibility == View.GONE || visibility == View.INVISIBLE)
        visibility = View.VISIBLE
}

fun View.hide() {
    if (visibility == View.VISIBLE)
        visibility = View.GONE
}

fun ImageView.setUrl(url: String?) {
    url?.let {
        Picasso.get().load(it).placeholder(R.drawable.place_holder).into(this)
    } ?: kotlin.run { this.setImageResource(R.drawable.place_holder) }
}
