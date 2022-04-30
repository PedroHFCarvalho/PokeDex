package com.carvalho.pokedex.adapter.helpers

import com.carvalho.pokedex.model.pokemon.PokeTransfer

interface PokemonItemClickListener {
    fun onPokemonClicked(pokemon: PokeTransfer)
}