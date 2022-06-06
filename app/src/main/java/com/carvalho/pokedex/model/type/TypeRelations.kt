package com.carvalho.pokedex.model.type

import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.google.gson.annotations.SerializedName

data class TypeRelations(
    @SerializedName("no_damage_to")
    val noDamageTo: List<NamedAPIResource>,
    @SerializedName("half_damage_to")
    val halfDamageTo: List<NamedAPIResource>,
    @SerializedName("double_damage_to")
    val doubleDamageTo: List<NamedAPIResource>,
    @SerializedName("move_damage_class")
    val moveDamageClass: List<NamedAPIResource>,
    @SerializedName("no_damage_from")
    val noDamageFrom: List<NamedAPIResource>,
    @SerializedName("half_damage_from")
    val halfDamageFrom: List<NamedAPIResource>,
    @SerializedName("double_damage_from")
    val doubleDamageFrom: List<NamedAPIResource>,
)
