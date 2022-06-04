package com.carvalho.pokedex.model.ability

import com.carvalho.pokedex.model.commonModel.FlavorText
import com.carvalho.pokedex.model.commonModel.VerboseEffect
import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("effect_entries")
    val effectEntries: List<VerboseEffect>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorText>,
    @SerializedName("pokemon")
    val pokemon: List<AbilityPokemon>,
)