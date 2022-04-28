package com.carvalho.pokedex.model.commonModel

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: NamedAPIResource,
)
