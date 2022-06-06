package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterAppPager
import com.carvalho.pokedex.adapter.AdapterCarousel
import com.carvalho.pokedex.adapter.helpers.PokemonSetBackgroundColor
import com.carvalho.pokedex.databinding.FragmentPokemonBinding
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.google.android.material.tabs.TabLayoutMediator


class PokemonFragment : Fragment() {

    private lateinit var binding: FragmentPokemonBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var appPagerAdapter: AdapterAppPager
    private lateinit var adapterCarousel: AdapterCarousel

    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(layoutInflater, container, false)

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
            binding.tvName.text =
                pokemonSelec?.name!!.replaceFirstChar { it.uppercase() }
                    .replace("-m", "♂")
                    .replace("-f", "♀")
            binding.imBackgroundType.setBackgroundResource(
                PokemonSetBackgroundColor.setColor(
                    pokemonSelec?.types!![0].type.name
                )
            )
            setupLayoutCarousel()
            setNumOrder()
        }
    }

    private fun getContentsForPreview() {
        viewModel.getPokemonByNameForPreview(viewModel.pokeTransfer!!.name)
    }

    private fun setNumOrder() {
        when (pokemonSelec!!.id.toString().length) {
            1 -> binding.tvNumOrder.text = "#00${pokemonSelec!!.id}"
            2 -> binding.tvNumOrder.text = "#0${pokemonSelec!!.id}"
            else -> binding.tvNumOrder.text = "#${pokemonSelec!!.id}"
        }
    }

    private fun setupLayoutMenu() {
        val titles = arrayListOf("Base", "Moves", "Evolution")
        appPagerAdapter = AdapterAppPager(this)
        binding.vpInfoPokemon.adapter = appPagerAdapter
        TabLayoutMediator(binding.tlNavPokemon, binding.vpInfoPokemon) { tab, position ->
            tab.text = titles[position]
        }.attach()
        binding.vpInfoPokemon.isUserInputEnabled = false
    }

    private fun setupLayoutCarousel() {
        adapterCarousel = AdapterCarousel(requireContext())
        adapterCarousel.setPokemonInAdapter(pokemonSelec!!)

        binding.carouselPokemon.adapter = adapterCarousel

        binding.carouselPokemon.setInfinite(false)
        binding.carouselPokemon.set3DItem(false)
        binding.carouselPokemon.setIntervalRatio(1F)
        binding.carouselPokemon.setAlpha(true)
    }

    override fun onResume() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        getContentsForPreview()
        super.onResume()
    }

    override fun onPause() {
        (activity as AppCompatActivity).supportActionBar?.show()
        super.onPause()
    }

}