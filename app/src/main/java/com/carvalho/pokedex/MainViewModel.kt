package com.carvalho.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carvalho.pokedex.model.evolution.chains.EvolutionChain
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.species.PokemonSpecies
import com.carvalho.pokedex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _responsePokemon = MutableLiveData<Response<Pokemon>>()
    var responsePokemon: LiveData<Response<Pokemon>> = _responsePokemon

    private val _responsePokemonSearch = MutableLiveData<Response<Pokemon>>()
    var responsePokemonSearch: LiveData<Response<Pokemon>> = _responsePokemonSearch

    private val _responsePokemonSpecie: MutableLiveData<Response<PokemonSpecies>> =
        MutableLiveData<Response<PokemonSpecies>>()
    var responsePokemonSpecie: LiveData<Response<PokemonSpecies>> = _responsePokemonSpecie

    private val _responseEvolution: MutableLiveData<Response<EvolutionChain>> =
        MutableLiveData<Response<EvolutionChain>>()
    var responseEvolution: LiveData<Response<EvolutionChain>> = _responseEvolution

    private var _responsePokemonEvolution: MutableLiveData<Response<Pokemon>> =
        MutableLiveData<Response<Pokemon>>()
    var responsePokemonEvolution: LiveData<Response<Pokemon>> = _responsePokemonEvolution

    var pokemonSelec: Pokemon? = null

    fun getPokemonByNumber(number: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonByNumber(number)
                _responsePokemon.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonByName(name)
                _responsePokemonSearch.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }

    fun getPokemonByNameForEvolution(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonByName(name)
                _responsePokemonEvolution!!.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }

    fun getSpecieByName(name: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSpecieByName(name)
                _responsePokemonSpecie.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }

    fun getEvolutionByID(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getEvolutionByID(id)
                _responseEvolution?.value = response
            } catch (e: Exception) {
                Log.e("Err", e.message.toString())
            }
        }
    }

}