package com.carvalho.pokedex.model.pokemon.gameVersion

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class VersionGameIndex(
    @SerializedName("game_index")
    val game_index: Int,
    @SerializedName("version")
    val version: NamedAPIResource,
)
