package com.example.myapplication.Rick

import com.google.gson.annotations.SerializedName

data class Rick(
    val id: Int,
    val name: String,
    @SerializedName("image")
    val imageUrl: String,
)