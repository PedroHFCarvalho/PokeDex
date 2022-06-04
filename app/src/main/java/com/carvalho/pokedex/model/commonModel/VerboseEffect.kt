package com.carvalho.pokedex.model.commonModel

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class VerboseEffect(
    @SerializedName("effect")
    val effect: String,
    @SerializedName("short_effect")
    val shortEffect: String,
    @SerializedName("language")
    val language: NamedAPIResource,
)
