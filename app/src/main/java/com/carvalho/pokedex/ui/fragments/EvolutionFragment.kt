package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private var hierarchy = mutableListOf<List<String>>()
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

            if (listPresentation.size == 0 || listPresentation.isNullOrEmpty()) {
                notHasEvolution()
            }
            pokemonAdapter.setList(listPresentation)
            isLoadingFalse()
            listPresentation.clear()
        }, 2000)

    }

    private fun notHasEvolution() {
        binding.tvNotEvolution.visibility = View.VISIBLE
    }

    private fun listDistinctByHierarchy(): MutableList<List<PokeTransfer?>> {
        val listPresentation = mutableListOf<List<PokeTransfer?>>()
        hierarchy.forEach {
            val presentation = mutableListOf<PokeTransfer?>()
            it.forEach {
                presentation.add(pokemonHierarchy.findLast { pokemon ->
                    pokemon.name.contains(it)
                })
            }
            Log.d("list", presentation.toString())
            listPresentation.add(presentation)
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

    private fun getStringsInPokemon(list: List<String>) {
        list.forEach {
            viewModel.getPokemonByNameForEvolution(it)
        }
    }

    private fun evolutionHierarchy() {
        val support: ChainLink
        hierarchy.clear()
        if (evolutionTo.chain.evolves_to.isNotEmpty()) {
            support = evolutionTo.chain

            support.evolves_to.forEach {
                val listEvolutionTo = mutableListOf<String>()

                listEvolutionTo.add(support.species.name)
                listEvolutionTo.add(it.species.name)

                hierarchy.add(listEvolutionTo)
                getStringsInPokemon(listEvolutionTo)

                detectEvolution(it)

            }
        }
        Log.d("Evo", hierarchy.toString())

    }

    private fun detectEvolution(chainLink: ChainLink): String {
        var result = ""
        if (chainLink.evolves_to.isNotEmpty()) {
            chainLink.evolves_to.forEach {

                val listEvolutionTo = mutableListOf<String>()
                val evolutionTo = detectEvolution(it)

                listEvolutionTo.add(chainLink.species.name)
                listEvolutionTo.add(evolutionTo)

                hierarchy.add(listEvolutionTo)
                getStringsInPokemon(listEvolutionTo)

                result = it.species.name
            }
        } else {
            result = chainLink.species.name
        }
        return result
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




