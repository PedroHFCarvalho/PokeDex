package com.carvalho.pokedex.model.type

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class Types(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("damage_relations")
    val damageRelations: TypeRelations,
    @SerializedName("move_damage_class")
    val moveDamageClass: NamedAPIResource,
)
