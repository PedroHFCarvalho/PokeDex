package com.carvalho.pokedex.model.evolution.chains.link

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.carvalho.pokedex.model.evolution.chains.datail.EvolutionDetail
import com.carvalho.pokedex.model.species.PokemonSpecies
import com.google.gson.annotations.SerializedName

data class ChainLink(
    @SerializedName("is_baby")
    val is_baby: Boolean,
    @SerializedName("species")
    val species: NamedAPIResource,
    @SerializedName("evolution_details")
    val evolution_details: List<EvolutionDetail>,
    @SerializedName("evolves_to")
    val evolves_to: List<ChainLink>,
)
