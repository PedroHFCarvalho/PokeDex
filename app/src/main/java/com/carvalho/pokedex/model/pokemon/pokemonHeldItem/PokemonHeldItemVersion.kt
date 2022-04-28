package com.carvalho.pokedex.model.pokemon.pokemonHeldItem

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonHeldItemVersion(
    @SerializedName("version")
    val version: NamedAPIResource,
    @SerializedName("rarity")
    val rarity: Int,
)
