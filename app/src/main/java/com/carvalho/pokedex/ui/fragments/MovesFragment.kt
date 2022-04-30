package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterAbility
import com.carvalho.pokedex.adapter.AdapterMoves
import com.carvalho.pokedex.databinding.FragmentMovesBinding
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.pokemon.ability.PokemonAbility
import com.carvalho.pokedex.model.pokemon.move.PokemonMove

class MovesFragment : Fragment() {

    private lateinit var binding: FragmentMovesBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var layoutManagerLinear: LinearLayoutManager

    private var listAbility: MutableList<PokemonAbility> = mutableListOf()
    private var listMove: MutableList<PokemonMove> = mutableListOf()

    private lateinit var pokemonAdapterAbility: AdapterAbility
    private lateinit var pokemonAdapterMove: AdapterMoves

    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovesBinding.inflate(layoutInflater, container, false)

        pokemonSelec = viewModel.pokemonSelec.value!!.body()
        recoverData()

        setupPokemonListAbility()
        setupPokemonListMoves()

        return binding.root
    }

    private fun recoverData() {
        listAbility.addAll(pokemonSelec!!.abilities)
        listMove.addAll(pokemonSelec!!.moves)
    }

    private fun setupPokemonListMoves() {
        binding.rvMoves.setHasFixedSize(true)
        layoutManagerLinear = LinearLayoutManager(context)
        binding.rvMoves.layoutManager = layoutManagerLinear
        getPageMoves()
    }

    private fun getPageMoves() {


        if (::pokemonAdapterMove.isInitialized) {
            pokemonAdapterMove.setList(listMove)
        } else {
            pokemonAdapterMove = AdapterMoves(requireContext())
            binding.rvMoves.adapter = pokemonAdapterMove
            pokemonAdapterMove.setList(listMove)
        }
        for (i in 0..listMove.size) {
            AdapterMoves(requireContext()).setList(listMove)
        }


    }


    private fun setupPokemonListAbility() {
        binding.rvAbility.setHasFixedSize(true)
        layoutManagerLinear = LinearLayoutManager(context)
        binding.rvAbility.layoutManager = layoutManagerLinear
        getPageAbility()
    }

    private fun getPageAbility() {

        if (::pokemonAdapterAbility.isInitialized) {
            pokemonAdapterAbility.setList(listAbility)
            //Log.v("Sucess", listAbility.toString())
        } else {
            pokemonAdapterAbility = AdapterAbility(requireContext())
            binding.rvAbility.adapter = pokemonAdapterAbility
            pokemonAdapterAbility.setList(listAbility)
        }
        for (i in 0..listAbility.size) {
            AdapterAbility(requireContext()).setList(listAbility)
            Log.d("Succs", i.toString())
        }
    }

}
