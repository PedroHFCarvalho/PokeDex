package com.carvalho.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.adapter.helpers.PokemonSetBackgroudColor
import com.carvalho.pokedex.databinding.CardviewStatusBinding
import com.carvalho.pokedex.model.pokemon.stat.PokemonStat
import com.carvalho.pokedex.model.pokemon.type.PokemonType

class AdapterStatus(val context: Context) :
    RecyclerView.Adapter<AdapterStatus.PokemonViewHolder>() {
    var pokemon = emptyList<PokemonStat>()

    class PokemonViewHolder(var binding: CardviewStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewStatusBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        holder.binding.progressBar.progress = pokemon[position].base_stat

        holder.binding.tvNumberStatus.text = pokemon[position].base_stat.toString()

        holder.binding.tvStatus.text =
            pokemon[position].stat.name.replaceFirstChar { it.uppercase() }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: MutableList<PokemonStat>) {
        pokemon = list
        notifyDataSetChanged()
    }


}