package com.bosta.androidtask_bosta.presentation.utils

import android.view.View


fun View.show() {
    if (visibility == View.GONE || visibility == View.INVISIBLE)
        visibility = View.VISIBLE
}

fun View.hide() {
    if (visibility == View.VISIBLE)
        visibility = View.GONE
}
