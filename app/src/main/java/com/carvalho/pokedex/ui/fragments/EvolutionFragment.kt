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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.AdapterEvolution
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.databinding.FragmentEvolutionBinding
import com.carvalho.pokedex.model.evolution.chains.EvolutionChain
import com.carvalho.pokedex.model.evolution.chains.link.ChainLink
import com.carvalho.pokedex.model.pokemon.PokeTransfer
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.species.PokemonSpecies


class EvolutionFragment : Fragment(), PokemonItemClickListener {


    private lateinit var binding: FragmentEvolutionBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var evolutionTo: EvolutionChain

    private var listSpecie: PokemonSpecies? = null
    private var hierarchy = mutableListOf<String>()
    private var pokemonHierarchy = mutableListOf<PokeTransfer>()
    private var keyForEvoluiton: String? = null

    private var isLoading = false
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var pokemonAdapter: AdapterEvolution

    private lateinit var pokeTransfer: PokeTransfer
    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEvolutionBinding.inflate(layoutInflater, container, false)

        viewModel.pokemonSelec.observe(viewLifecycleOwner) {
            pokemonSelec = it.body()
            recoverData()
            setupPokemonList()
        }


        viewModel.responsePokemonSpecie.observe(viewLifecycleOwner) {
            if (listSpecie != it.body()) {
                listSpecie = it.body()!!
                val urlStr =
                    Regex("[0-9]+").findAll(listSpecie!!.evolution_chain.url)
                        .map(MatchResult::value)
                        .toList()
                keyForEvoluiton = urlStr.last()
                Log.v("Specie", it.body()!!.name)
                viewModel.getEvolutionByID(keyForEvoluiton!!.toInt())
            }
        }

        viewModel.responseEvolution.observe(viewLifecycleOwner) {
            evolutionTo = it.body()!!
            evolutionHierarchy()
        }

        viewModel.responsePokemonEvolution.observe(viewLifecycleOwner) {
            pokemonSelec = it.body()!!
            pokeTransfer = PokeTransfer(
                pokemonSelec!!.id,
                pokemonSelec!!.name,
                pokemonSelec!!.order,
                pokemonSelec!!.sprites,
                pokemonSelec!!.types
            )
            pokemonHierarchy.add(pokeTransfer)

            pokemonHierarchy.sortBy { pokemon -> pokemon.order }
            Log.v("List", it.body().toString())
        }

        return binding.root
    }

    private fun setupPokemonList() {
        binding.rvEvolution.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.rvEvolution.layoutManager = layoutManager

        getPage()
    }

    private fun getPage() {
        isLoading = true
        binding.tvNotEvolution.visibility = View.INVISIBLE
        binding.inLoadingEvolution.pbPaginationList.visibility = View.VISIBLE

        recoverData()

        Handler(Looper.getMainLooper()).postDelayed({

            pokemonAdapter = AdapterEvolution(requireContext(), this)
            binding.rvEvolution.adapter = pokemonAdapter

            val listApresentation = mutableListOf<PokeTransfer?>()

            hierarchy.forEach {
                listApresentation.add(pokemonHierarchy.find { pokemon -> pokemon.name.contains(it) })
            }
            if (listApresentation.size == 1 || listApresentation.isNullOrEmpty()) {
                binding.tvNotEvolution.visibility = View.VISIBLE
            }
            pokemonAdapter.setList(listApresentation.distinctBy { it?.order } as List<PokeTransfer?>)
            Log.v("Pag1", listApresentation.toString())

            isLoading = false
            binding.inLoadingEvolution.pbPaginationList.visibility = View.GONE
            listApresentation.clear()
        }, 1500)

    }


    private fun recoverData() {
        viewModel.getPokemonByName(viewModel.pokeTransfer!!.name)

        listSpecie = null
        Log.v("identi", pokemonSelec!!.name)

        viewModel.getSpecieByName(pokemonSelec!!.name)

    }

    private fun convertPokemon() {
        hierarchy.forEach {
            viewModel.getPokemonByNameForEvolution(it)
        }
    }

    private fun evolutionHierarchy() {
        var support: ChainLink
        hierarchy.clear()

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
        Log.d("Evo", hierarchy.toString())
        convertPokemon()
    }

    override fun onPokemonClicked(pokemon: PokeTransfer) {
        viewModel.pokeTransfer = pokemon
        findNavController().navigate(
            R.id.action_pokemonFragment_self, null,
            NavOptions.Builder().setPopUpTo(R.id.pokemonFragment, true)
                .build()
        )

    }

}



