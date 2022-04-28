package com.carvalho.pokedex.model.species.genus

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class Genus(
    @SerializedName("genus")
    val genus: String,
    @SerializedName("language")
    val language: NamedAPIResource,
)
