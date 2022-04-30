package com.carvalho.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.adapter.helpers.PokemonSetBackgroudColor
import com.carvalho.pokedex.databinding.CardviewTypesBinding
import com.carvalho.pokedex.model.pokemon.type.PokemonType

class AdapterTypes(val context: Context): RecyclerView.Adapter<AdapterTypes.PokemonViewHolder>()  {

    var pokemon = emptyList<PokemonType>()

    class PokemonViewHolder(var binding: CardviewTypesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewTypesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val color = PokemonSetBackgroudColor.setColor(pokemon[position].type.name)
        holder.binding.cvTypes.setCardBackgroundColor(ContextCompat.getColor(context!!, color))

        holder.binding.tvTypes.text = pokemon[position].type.name.replaceFirstChar {it.uppercase()}
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: MutableList<PokemonType>) {
        pokemon = list
        notifyDataSetChanged()
    }


}