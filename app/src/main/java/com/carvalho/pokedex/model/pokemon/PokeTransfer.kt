package com.carvalho.pokedex.model.pokemon

import com.carvalho.pokedex.model.pokemon.sprites.PokemonSprites
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.google.gson.annotations.SerializedName

data class PokeTransfer(
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: PokemonSprites,
    val types: List<PokemonType>,
)
