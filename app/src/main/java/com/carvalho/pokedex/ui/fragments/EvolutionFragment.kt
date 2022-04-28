package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.AdapterListagem
import com.carvalho.pokedex.databinding.FragmentEvolutionBinding
import com.carvalho.pokedex.databinding.FragmentListBinding
import com.carvalho.pokedex.model.evolution.chains.EvolutionChain
import com.carvalho.pokedex.model.evolution.chains.link.ChainLink
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.carvalho.pokedex.model.species.PokemonSpecies


class EvolutionFragment : Fragment() {


    private lateinit var binding: FragmentEvolutionBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var evolutionTo: EvolutionChain

    private lateinit var listSpecie: PokemonSpecies
    private var hierarchy = mutableListOf<String>()
    private var pokemonHierarchy = mutableListOf<Pokemon>()
    private var keyForEvoluiton: String? = null


    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEvolutionBinding.inflate(layoutInflater, container, false)

        recoverData()

        return binding.root
    }

    private fun recoverData() {
        pokemonSelec = viewModel.pokemonSelec

        if (pokemonSelec != null) {
            viewModel.getSpecieByName(pokemonSelec!!.name)
        }
        viewModel.responsePokemonSpecie.observe({ lifecycle }) {
            listSpecie = it.body()!!
            keyForEvoluiton =
                listSpecie.evolution_chain.url[listSpecie.evolution_chain.url.length - 2].toString()
            if (keyForEvoluiton != null) {
                viewModel.getEvolutionByID(keyForEvoluiton!!.toInt())
            }
        }

        viewModel.responseEvolution.observe({ lifecycle }) {
            evolutionTo = it.body()!!
            hierarchy.clear()

            evolutionHierarchy()
        }

        viewModel.responsePokemonEvolution.observe({ lifecycle }) {
            pokemonHierarchy.add(it.body()!!)
            pokemonHierarchy.sortBy { pokemon -> pokemon.order }
            Log.v("List", it.body().toString())
        }

    }

    private fun convertePokemon() {
        hierarchy.forEach {
            viewModel.getPokemonByNameForEvolution(it)
        }

    }

    private fun evolutionHierarchy() {
        var support: ChainLink

        if (evolutionTo.chain.evolves_to.isNotEmpty()) {
            support = evolutionTo.chain
            hierarchy.add(support.species.name)
            Log.v("Here", "1")
            for (one in 0 until support.evolves_to.size) {
                support = support.evolves_to[one]

                if (support.evolves_to.isNotEmpty()) {
                    hierarchy.add(support.species.name)
                    Log.v("Here", "2")
                    for (two in 0 until support.evolves_to.size) {
                        support = support.evolves_to[two]

                        if (support.evolves_to.isNotEmpty()) {
                            hierarchy.add(support.species.name)
                            Log.v("Here", "3")
                            for (three in 0..support.evolves_to.size) {
                                hierarchy.add(support.species.name)
                            }
                        } else {
                            hierarchy.add(support.species.name)
                        }
                    }
                } else {
                    hierarchy.add(support.species.name)
                }
            }
        } else {
            hierarchy.add(evolutionTo.chain.species.name)
        }
        convertePokemon()
        Log.d("Evo", hierarchy.toString())
    }

}



