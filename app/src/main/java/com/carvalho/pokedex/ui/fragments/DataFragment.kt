package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterStatus
import com.carvalho.pokedex.adapter.AdapterTypes
import com.carvalho.pokedex.databinding.FragmentDataBinding
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.pokemon.ability.PokemonAbility
import com.carvalho.pokedex.model.pokemon.stat.PokemonStat
import com.carvalho.pokedex.model.pokemon.type.PokemonType

class DataFragment : Fragment() {

    private lateinit var binding: FragmentDataBinding
    private lateinit var layoutManagerGrid: GridLayoutManager
    private lateinit var layoutManagerLinear: LinearLayoutManager
    private lateinit var pokemonAdapterTypes: AdapterTypes

    private lateinit var pokemonAdapterStats: AdapterStatus
    private val viewModel: MainViewModel by activityViewModels()

    private var listTypes: MutableList<PokemonType> = mutableListOf()
    private var listStats: MutableList<PokemonStat> = mutableListOf()
    private var listAbility: MutableList<PokemonAbility> = mutableListOf()
    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataBinding.inflate(layoutInflater, container, false)

        pokemonSelec = viewModel.pokemonSelec.value!!.body()
        recoverData()

        setupPokemonListTypes()
        setupPokemonListStats()

        return binding.root
    }

    private fun recoverData() {

        listTypes.addAll(pokemonSelec!!.types)
        listStats.addAll(pokemonSelec!!.stats)
        listAbility.addAll(pokemonSelec!!.abilities)

        if (pokemonSelec != null) {
            binding.tvHeight.text = "${pokemonSelec?.height?.div(10.0)} M"
            binding.tvWeight.text = "${pokemonSelec?.weight?.div(10.0)} Kg"
        }

    }


    private fun setupPokemonListStats() {
        binding.rvStatus.setHasFixedSize(true)
        layoutManagerLinear = LinearLayoutManager(context)
        binding.rvStatus.layoutManager = layoutManagerLinear
        getPageStats()
    }

    private fun getPageStats() {

        if (::pokemonAdapterStats.isInitialized) {
            pokemonAdapterStats.setList(listStats)
        } else {
            pokemonAdapterStats = AdapterStatus(requireContext())
            binding.rvStatus.adapter = pokemonAdapterStats
            pokemonAdapterStats.setList(listStats)
        }
        for (i in 0..listStats.size) {
            AdapterStatus(requireContext()).setList(listStats)
        }

    }

    private fun setupPokemonListTypes() {
        binding.rvTypes.setHasFixedSize(true)
        layoutManagerGrid = GridLayoutManager(context, 2)
        binding.rvTypes.layoutManager = layoutManagerGrid
        getPageTypes()
    }

    private fun getPageTypes() {

        if (::pokemonAdapterTypes.isInitialized) {
            pokemonAdapterTypes.setList(listTypes)
        } else {
            pokemonAdapterTypes = AdapterTypes(requireContext())
            binding.rvTypes.adapter = pokemonAdapterTypes
            pokemonAdapterTypes.setList(listTypes)
        }
        for (i in 0..listTypes.size) {
            AdapterTypes(requireContext()).setList(listTypes)

        }

    }

}