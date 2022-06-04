package com.carvalho.pokedex.model.moves

import com.carvalho.pokedex.model.commonModel.FlavorText
import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.carvalho.pokedex.model.commonModel.VerboseEffect
import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("accuracy")
    val accuracy: Int,
    @SerializedName("effect_chance")
    val effectChance: Int,
    @SerializedName("pp")
    val pp: Int,
    @SerializedName("power")
    val power: Int,
    @SerializedName("type")
    val type: NamedAPIResource,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorText>,
    @SerializedName("effect_entries")
    val effectEntries: List<VerboseEffect>,
)
