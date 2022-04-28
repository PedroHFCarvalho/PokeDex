package com.carvalho.pokedex.model.commonModel

import com.google.gson.annotations.SerializedName

data class APIResource(
    @SerializedName("url")
    val url: String
)
