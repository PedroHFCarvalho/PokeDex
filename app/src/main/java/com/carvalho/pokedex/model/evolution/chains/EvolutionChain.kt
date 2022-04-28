package com.carvalho.pokedex.model.evolution.chains

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.carvalho.pokedex.model.evolution.chains.link.ChainLink
import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    @SerializedName("id")
    val id: Int,
    @SerializedName("baby_trigger_item")
    val baby_trigger_item: NamedAPIResource,
    @SerializedName("chain")
    val chain: ChainLink,
)
