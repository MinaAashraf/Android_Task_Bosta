package com.bosta.androidtask_bosta.presentation.profile

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bosta.androidtask_bosta.domain.model.UserAddress
import com.bosta.androidtask_bosta.presentation.utils.hide
import com.bosta.androidtask_bosta.presentation.utils.show


@BindingAdapter("address")
fun bindAddressText(textView: TextView, address: UserAddress?) {
    address?.let {
        var addressString = ""
        textView.text = addressString
        textView.show()
    } ?: run { textView.hide() }

}