package com.carvalho.pokedex.model.pokemon.form

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonFormType(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: NamedAPIResource,
)

