package com.carvalho.pokedex.model.evolution.chains.datail

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class EvolutionDetail(
    @SerializedName("item")
    val item: NamedAPIResource,
    @SerializedName("trigger")
    val trigger: NamedAPIResource,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("held_item")
    val held_item: NamedAPIResource,
    @SerializedName("known_move")
    val known_move: NamedAPIResource,
    @SerializedName("known_move_type")
    val known_move_type: NamedAPIResource,
    @SerializedName("location")
    val location: NamedAPIResource,
    @SerializedName("min_level")
    val min_level: Int,
    @SerializedName("min_happiness")
    val min_happiness: Int,
    @SerializedName("min_beauty")
    val min_beauty: Int,
    @SerializedName("min_affection")
    val min_affection: Int,
    @SerializedName("needs_overworld_rain")
    val needs_overworld_rain: Boolean,
    @SerializedName("party_species")
    val party_species: NamedAPIResource,
    @SerializedName("party_type")
    val party_type: NamedAPIResource,
    @SerializedName("relative_physical_stats")
    val relative_physical_stats: Int,
    @SerializedName("time_of_day")
    val time_of_day: String,
    @SerializedName("trade_species")
    val trade_species: NamedAPIResource,
    @SerializedName("turn_upside_down")
    val turn_upside_down: Boolean,
)