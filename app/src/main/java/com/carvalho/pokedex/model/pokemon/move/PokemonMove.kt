package com.carvalho.pokedex.model.pokemon.move

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonMove(
    @SerializedName("move")
    val move: NamedAPIResource,
    @SerializedName("version_group_details")
    val version_details: List<PokemonMoveVersion>,
)
