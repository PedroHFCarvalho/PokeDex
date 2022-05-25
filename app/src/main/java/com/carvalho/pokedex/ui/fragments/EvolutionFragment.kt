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
    private lateinit var pokemonAdapter: AdapterEvolution

    private var hierarchy = mutableListOf<String>()
    private var pokemonHierarchy = mutableListOf<PokeTransfer>()

    private var isLoading = false

    private var listSpecie: PokemonSpecies? = null
    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEvolutionBinding.inflate(layoutInflater, container, false)

        viewModel.pokemonSelec.observe(viewLifecycleOwner) {
            setPokemon(it.body()!!)
            recoverDataPokemonAndSpecie()
            includeContentsInPageEvolution()
        }

        viewModel.responsePokemonSpecie.observe(viewLifecycleOwner) {
            getEvolutionBySpecie(it.body()!!)
        }

        viewModel.responseEvolution.observe(viewLifecycleOwner) {
            evolutionTo = it.body()!!
            evolutionHierarchy()
        }

        viewModel.responsePokemonEvolution.observe(viewLifecycleOwner) {
            setPokemon(it.body()!!)
            pokemonHierarchy.add(buildPokeTransfer(it.body()!!))
            pokemonHierarchy.sortBy { pokemon -> pokemon.order }
        }

        return binding.root
    }

    private fun getEvolutionBySpecie(pokemonSpecie: PokemonSpecies) {
        if (listSpecie != pokemonSpecie) {
            listSpecie = pokemonSpecie
            val urlStr =
                Regex("[0-9]+").findAll(listSpecie!!.evolution_chain.url)
                    .map(MatchResult::value)
                    .toList()
            val id = urlStr.last()
            getContentForEvolution(id.toInt())
        }
    }

    private fun getContentForEvolution(id: Int) {
        viewModel.getEvolutionByID(id)
    }

    private fun setPokemon(pokemon: Pokemon) {
        pokemonSelec = pokemon
    }

    private fun buildPokeTransfer(pokemon: Pokemon): PokeTransfer {
        return PokeTransfer(
            pokemon.id,
            pokemon.name,
            pokemon.order,
            pokemon.sprites,
            pokemon.types
        )
    }

    private fun setupLayoutList() {
        binding.rvEvolution.setHasFixedSize(true)
        binding.rvEvolution.layoutManager = LinearLayoutManager(context)
    }

    private fun includeContentsInPageEvolution() {
        isLoadingTrue()
        binding.tvNotEvolution.visibility = View.INVISIBLE
        recoverDataPokemonAndSpecie()

        Handler(Looper.getMainLooper()).postDelayed({
            setAdapterEvolution()
            val listPresentation = listDistinctByHierarchy()

            if (listPresentation.size == 1 || listPresentation.isNullOrEmpty()) {
                notHasEvolution()
            }
            pokemonAdapter.setList(listPresentation.toList())
            isLoadingFalse()
            listPresentation.clear()
        }, 2000)

    }

    private fun notHasEvolution() {
        binding.tvNotEvolution.visibility = View.VISIBLE
    }

    private fun listDistinctByHierarchy(): MutableList<PokeTransfer?> {
        val listPresentation = mutableListOf<PokeTransfer?>()
        hierarchy.forEach {
            listPresentation.add(pokemonHierarchy.find { pokemon -> pokemon.name.contains(it) })
        }
        pokemonHierarchy.clear()
        return listPresentation
    }

    private fun setAdapterEvolution() {
        pokemonAdapter = AdapterEvolution(requireContext(), this)
        binding.rvEvolution.adapter = pokemonAdapter
    }


    private fun recoverDataPokemonAndSpecie() {
        viewModel.getPokemonByName(viewModel.pokeTransfer!!.name)
        viewModel.getSpecieByName(pokemonSelec!!.name)
        listSpecie = null
    }

    private fun getStringsInPokemon() {
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
        getStringsInPokemon()
    }

    private fun isLoadingTrue() {
        isLoading = true
        binding.inLoadingEvolution.pbPaginationList.visibility = View.VISIBLE

    }

    private fun isLoadingFalse() {
        isLoading = false
        binding.inLoadingEvolution.pbPaginationList.visibility = View.GONE
    }

    override fun onPokemonClicked(pokemon: PokeTransfer) {
        viewModel.pokeTransfer = pokemon

        findNavController().navigate(
            R.id.action_pokemonFragment_self
        )

    }

    private fun setProperHeightOfView() {
        val layoutView = view
        if (layoutView != null) {
            val layoutParams = layoutView.layoutParams
            if (layoutParams != null) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutView.requestLayout()
            }
        }
    }

    override fun onResume() {
        setupLayoutList()
        setProperHeightOfView()

        super.onResume()
    }


}



