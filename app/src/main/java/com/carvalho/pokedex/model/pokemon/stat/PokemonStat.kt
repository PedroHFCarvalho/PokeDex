package com.carvalho.pokedex.model.pokemon.stat

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonStat(
    @SerializedName("stat")
    val stat: NamedAPIResource,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("base_stat")
    val base_stat: Int,
)

