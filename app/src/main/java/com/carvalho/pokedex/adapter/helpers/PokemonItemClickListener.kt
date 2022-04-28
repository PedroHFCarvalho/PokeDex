package com.carvalho.pokedex.adapter.helpers

import com.carvalho.pokedex.model.pokemon.Pokemon

interface PokemonItemClickListener {
    fun onPokemonClicked(pokemon: Pokemon)
}