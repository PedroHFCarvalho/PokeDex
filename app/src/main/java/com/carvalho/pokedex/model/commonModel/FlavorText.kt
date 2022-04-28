package com.carvalho.pokedex.model.commonModel

import com.google.gson.annotations.SerializedName

data class FlavorText(
    @SerializedName("flavor_text")
    val flavor_text: String,
    @SerializedName("language")
    val language: NamedAPIResource,
    @SerializedName("version")
    val version: NamedAPIResource,
) {
}