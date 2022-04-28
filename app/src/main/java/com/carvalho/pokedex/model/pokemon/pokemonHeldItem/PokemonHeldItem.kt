package com.carvalho.pokedex.model.pokemon.pokemonHeldItem

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

class PokemonHeldItem(
    @SerializedName("item")
    val item: NamedAPIResource,
    @SerializedName("version_details")
    val version_details: List<PokemonHeldItemVersion>,
)