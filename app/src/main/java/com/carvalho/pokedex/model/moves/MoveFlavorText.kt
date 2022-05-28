package com.carvalho.pokedex.model.moves

import com.google.gson.annotations.SerializedName

data class MoveFlavorText(
    @SerializedName("flavor_text")
    val flavor_text: String,
)
