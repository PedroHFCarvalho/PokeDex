package com.carvalho.pokedex.model.ability

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class AbilityPokemon(
    @SerializedName("is_hidden")
    val is_hidden: Boolean,
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("pokemon")
    val pokemon: NamedAPIResource,
)

