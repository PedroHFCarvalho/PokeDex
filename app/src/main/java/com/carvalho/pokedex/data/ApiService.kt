package com.carvalho.pokedex.data

import com.carvalho.pokedex.model.evolution.chains.EvolutionChain
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.species.PokemonSpecies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/{number}")
    suspend fun getPokemonByNumber(@Path("number") number: Int): Response<Pokemon>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<Pokemon>

    @GET("pokemon-species/{name}")
    suspend fun getSpecieByName(@Path("name") name: String): Response<PokemonSpecies>

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionByID(@Path("id") id: Int): Response<EvolutionChain>
}