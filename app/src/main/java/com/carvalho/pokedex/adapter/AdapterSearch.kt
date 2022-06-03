package com.carvalho.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.databinding.CardviewSearchBinding
import com.carvalho.pokedex.model.pokemon.PokeTransfer
import com.carvalho.pokedex.model.pokemon.Pokemon

class AdapterSearch(private val pokemonItemClickListener: PokemonItemClickListener) :
    RecyclerView.Adapter<AdapterSearch.PokemonViewHolder>() {

    var pokemon = emptyList<PokeTransfer>()

    class PokemonViewHolder(var binding: CardviewSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(pokemon[position].sprites.front_default)
            .into(holder.binding.imPokemonSearch)

        holder.binding.tvPokemonSearch.text =
            pokemon[position].name.replaceFirstChar { it.uppercase() }
                .replace("-m", "♂")
                .replace("-f", "♀")

        holder.itemView.setOnClickListener {
            pokemonItemClickListener.onPokemonClicked(pokemon[position])

        }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: List<PokeTransfer>) {
        pokemon = list.distinctBy {
            it.id
        }
        pokemon.sortedBy {
            it.id
        }
        notifyItemInserted(pokemon.size)
    }

}