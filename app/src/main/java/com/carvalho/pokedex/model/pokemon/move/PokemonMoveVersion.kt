package com.carvalho.pokedex.model.pokemon.move

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class PokemonMoveVersion(
    @SerializedName("move_learn_method")
    val move_learn_method: NamedAPIResource,
    @SerializedName("version_group")
    val version_group: NamedAPIResource,
    @SerializedName("level_learned_at")
    val level_learned_at: Int,
)
