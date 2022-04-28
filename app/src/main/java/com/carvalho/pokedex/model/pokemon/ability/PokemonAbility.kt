package com.carvalho.pokedex.model.pokemon.ability

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonAbility(
    @SerializedName("is_hidden")
    val is_hidden: Boolean,
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("ability")
    val ability: NamedAPIResource,
)
