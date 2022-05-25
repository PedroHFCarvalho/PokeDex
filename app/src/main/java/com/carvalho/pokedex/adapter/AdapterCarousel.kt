package com.carvalho.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.pokedex.databinding.ItemViewCarouselBinding
import com.carvalho.pokedex.model.pokemon.Pokemon

class AdapterCarousel(val context: Context) :
    RecyclerView.Adapter<AdapterCarousel.PokemonViewHolder>() {

    private var pokemonImage = emptyList<String>()

    class PokemonViewHolder(var binding: ItemViewCarouselBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            ItemViewCarouselBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        Glide.with(context).load(pokemonImage[position]).into(holder.binding.imageCarrousel)
    }

    override fun getItemCount(): Int {
        return pokemonImage.size
    }

    private fun buildList(pokemon: Pokemon) {
        val listSupport = ArrayList<String>()
        if (!pokemon.sprites.front_default.isNullOrBlank()) {
            listSupport.add(pokemon.sprites.front_default)
        }
        if (!pokemon.sprites.back_default.isNullOrBlank()) {
            listSupport.add(pokemon.sprites.back_default)
        }
        if (!pokemon.sprites.front_shiny.isNullOrBlank()) {
            listSupport.add(pokemon.sprites.front_shiny)
        }
        if (!pokemon.sprites.back_shiny.isNullOrBlank()) {
            listSupport.add(pokemon.sprites.back_shiny!!)
        }
        if (!pokemon.sprites.front_female.isNullOrBlank()) {
            listSupport.add(pokemon.sprites.front_female)
        }
        if (!pokemon.sprites.back_female.isNullOrBlank()) {
            listSupport.add(pokemon.sprites.back_female!!)
        }
        pokemonImage = listSupport
    }

    fun setPokemonInAdapter(pokemon: Pokemon) {
        buildList(pokemon)
        notifyDataSetChanged()
    }
}