package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterAppPager
import com.carvalho.pokedex.adapter.helpers.PokemonSetBackgroudColor
import com.carvalho.pokedex.databinding.FragmentPokemonBinding
import com.carvalho.pokedex.model.pokemon.PokeTransfer
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.pokemon.stat.PokemonStat
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.google.android.material.tabs.TabLayoutMediator

class PokemonFragment : Fragment() {

    private lateinit var binding: FragmentPokemonBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var appPagerAdapter: AdapterAppPager

    private var listTypes: MutableList<PokemonType> = mutableListOf()
    private var listStats: MutableList<PokemonStat> = mutableListOf()
    private val titles = arrayListOf("Base", "Moves", "Evolution")

    private var pokeTransfer: PokeTransfer? = null
    private var pokemonSelec: Pokemon? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(layoutInflater, container, false)

        viewModel.getPokemonByNameForPreview(viewModel.pokeTransfer!!.name)

        viewModel.pokemonSelec.observe(viewLifecycleOwner) {
            pokemonSelec = it.body()
            recoverData()
            setupTabLayout()
        }


        return binding.root
    }

    private fun recoverData() {

        if (pokemonSelec != null) {
            binding.tvName.text = pokemonSelec?.name!!.replaceFirstChar { it.uppercase() }
            binding.imBackgroundType.setBackgroundResource(
                PokemonSetBackgroudColor.setColor(
                    pokemonSelec?.types!![0].type.name
                )
            )
            Glide.with(this).load(pokemonSelec?.sprites?.front_default).into(binding.imPokemonFront)
        }

    }

    private fun setupTabLayout() {
        appPagerAdapter = AdapterAppPager(this)
        binding.vpInfoPokemon.adapter = appPagerAdapter
        TabLayoutMediator(binding.tlNavPokemon, binding.vpInfoPokemon) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }


}