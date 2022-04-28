package com.carvalho.pokedex.model.species.speciesDex

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonSpeciesDexEntry(
    @SerializedName("entry_number")
    val entry_number: Int,
    @SerializedName("pokedex")
    val pokedex: NamedAPIResource,
)
