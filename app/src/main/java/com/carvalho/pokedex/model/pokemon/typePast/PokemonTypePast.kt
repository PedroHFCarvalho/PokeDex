package com.carvalho.pokedex.model.pokemon.typePast

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.google.gson.annotations.SerializedName

data class PokemonTypePast(
    @SerializedName("generation")
    val generation: NamedAPIResource,
    @SerializedName("types")
    val types: List<PokemonType>,
)
