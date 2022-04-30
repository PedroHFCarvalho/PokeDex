package com.carvalho.pokedex.repository

import com.carvalho.pokedex.data.RetrofitInstance
import com.carvalho.pokedex.model.evolution.chains.EvolutionChain
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.species.PokemonSpecies
import retrofit2.Response

class Repository {

    suspend fun getPokemonByNumber(number: Int): Response<Pokemon> {
        return RetrofitInstance.api.getPokemonByNumber(number)
    }

    suspend fun getPokemonByName(name: String): Response<Pokemon> {
        return RetrofitInstance.api.getPokemonByName(name)
    }

    suspend fun getSpecieByName(name: String): Response<PokemonSpecies> {
        return RetrofitInstance.api.getSpecieByName(name)
    }

    suspend fun getEvolutionByID(id: Int): Response<EvolutionChain> {
        return RetrofitInstance.api.getEvolutionByID(id)
    }
}