package com.carvalho.pokedex.model.commonModel

import com.google.gson.annotations.SerializedName

data class Effect(
    @SerializedName("effect")
    val effect: String,
    @SerializedName("language")
    val language: NamedAPIResource,
)
