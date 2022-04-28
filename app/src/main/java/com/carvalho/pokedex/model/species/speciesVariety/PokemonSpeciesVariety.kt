package com.carvalho.pokedex.model.species.speciesVariety

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonSpeciesVariety(
    @SerializedName("is_default")
    val is_default: Boolean,
    @SerializedName("pokemon")
    val pokemon: NamedAPIResource,
)
