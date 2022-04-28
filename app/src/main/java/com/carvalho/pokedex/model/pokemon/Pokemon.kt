package com.carvalho.pokedex.model.pokemon

import com.carvalho.pokedex.model.pokemon.ability.PokemonAbility
import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.carvalho.pokedex.model.pokemon.gameVersion.VersionGameIndex
import com.carvalho.pokedex.model.pokemon.move.PokemonMove
import com.carvalho.pokedex.model.pokemon.sprites.PokemonSprites
import com.carvalho.pokedex.model.pokemon.stat.PokemonStat
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.carvalho.pokedex.model.pokemon.typePast.PokemonTypePast
import com.carvalho.pokedex.model.pokemon.pokemonHeldItem.PokemonHeldItem
import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("base_experience")
    val base_experience: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("is_default")
    val is_default: Boolean,
    @SerializedName("order")
    val order: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("abilities")
    val abilities: List<PokemonAbility>,
    @SerializedName("forms")
    val forms: List<NamedAPIResource>,
    @SerializedName("game_indices")
    val game_indices: List<VersionGameIndex>,
    @SerializedName("held_items")
    val held_items: List<PokemonHeldItem>,
    @SerializedName("location_area_encounters")
    val location_area_encounters: String,
    @SerializedName("moves")
    val moves: List<PokemonMove>,
    @SerializedName("past_types")
    val past_types: List<PokemonTypePast>,
    @SerializedName("sprites")
    val sprites: PokemonSprites,
    @SerializedName("species")
    val species: NamedAPIResource,
    @SerializedName("stats")
    val stats: List<PokemonStat>,
    @SerializedName("types")
    val types: List<PokemonType>,
)
