package com.carvalho.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.databinding.CardviewAbilityBinding
import com.carvalho.pokedex.model.pokemon.ability.PokemonAbility

class AdapterAbility(val context: Context) :
    RecyclerView.Adapter<AdapterAbility.PokemonViewHolder>() {

    var pokemon = emptyList<PokemonAbility>()

    class PokemonViewHolder(var binding: CardviewAbilityBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewAbilityBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        holder.binding.tvAbility.text = pokemon[position].ability.name.replaceFirstChar { it.uppercase() }

        holder.binding.radioButton.isChecked = pokemon[position].is_hidden
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: MutableList<PokemonAbility>) {
        pokemon = list
        notifyDataSetChanged()
    }

}