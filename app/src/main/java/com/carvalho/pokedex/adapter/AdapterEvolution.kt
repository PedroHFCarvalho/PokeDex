package com.carvalho.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.pokedex.databinding.CardviewEvolutionBinding
import com.carvalho.pokedex.model.pokemon.Pokemon

class AdapterEvolution(val context: Context) :
    RecyclerView.Adapter<AdapterEvolution.PokemonViewHolder>() {
    var pokemon = emptyList<Pokemon?>()

    class PokemonViewHolder(var binding: CardviewEvolutionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewEvolutionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        Glide.with(context)
            .load(pokemon[position]?.sprites?.front_default)
            .into(holder.binding.imPokemonOne)

        Glide.with(context)
            .load(pokemon[position + 1]?.sprites?.front_default)
            .into(holder.binding.imPokemonTwo)

    }

    override fun getItemCount(): Int {
        return pokemon.size - 1
    }

    fun setList(list: List<Pokemon?>) {
        pokemon = list.sortedBy { it?.order }
        notifyDataSetChanged()
    }


}