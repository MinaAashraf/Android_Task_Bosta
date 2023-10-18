package com.bosta.androidtask_bosta.domain.model

import com.google.gson.annotations.SerializedName

data class Album(
    val userId : Int? = null,
    @SerializedName("id")
    val albumId : Int? = null,
    val title : String? = null
)
