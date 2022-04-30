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
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.google.android.material.tabs.TabLayoutMediator

class PokemonFragment : Fragment() {

    private lateinit var binding: FragmentPokemonBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var appPagerAdapter: AdapterAppPager

    private var pokemonSelec: Pokemon? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(layoutInflater, container, false)

        getContentsForPreview()

        viewModel.pokemonSelec.observe(viewLifecycleOwner) {
            setPokemon(it.body()!!)
            setHeader()
            setupLayoutMenu()
        }
        return binding.root
    }

    private fun setPokemon(pokemon: Pokemon) {
        pokemonSelec = pokemon
    }

    private fun setHeader() {
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

    private fun getContentsForPreview() {
        viewModel.getPokemonByNameForPreview(viewModel.pokeTransfer!!.name)
    }

    private fun setupLayoutMenu() {
        val titles = arrayListOf("Base", "Moves", "Evolution")
        appPagerAdapter = AdapterAppPager(this)
        binding.vpInfoPokemon.adapter = appPagerAdapter
        TabLayoutMediator(binding.tlNavPokemon, binding.vpInfoPokemon) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }


}