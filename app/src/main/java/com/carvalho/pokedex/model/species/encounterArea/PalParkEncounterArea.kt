package com.carvalho.pokedex.model.species.encounterArea

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PalParkEncounterArea(
    @SerializedName("base_score")
    val base_score: Int,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("area")
    val area: NamedAPIResource,
)
