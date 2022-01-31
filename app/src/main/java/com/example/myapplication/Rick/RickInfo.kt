package com.example.myapplication.Rick

import com.google.gson.annotations.SerializedName

class RickInfo (
    val id: Int,
    val name: String,
    @SerializedName("image")
    val imageUrl: String,
    val status: String,
    val species: String
)