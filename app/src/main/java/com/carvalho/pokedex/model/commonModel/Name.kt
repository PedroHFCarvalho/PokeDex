package com.carvalho.pokedex.model.commonModel

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: NamedAPIResource,
)
