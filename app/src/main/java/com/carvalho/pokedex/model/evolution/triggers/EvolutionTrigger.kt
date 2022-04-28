package com.carvalho.pokedex.model.evolution.triggers

import com.carvalho.pokedex.model.commonModel.Name
import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class EvolutionTrigger(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("names")
    val names: List<Name>,
    @SerializedName("pokemon_species")
    val pokemon_species: List<NamedAPIResource>
)
