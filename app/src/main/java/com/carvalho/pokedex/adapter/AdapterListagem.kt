package com.carvalho.pokedex.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.adapter.helpers.PokemonSetBackgroudColor
import com.carvalho.pokedex.databinding.CardviewPreviewBinding
import com.carvalho.pokedex.model.pokemon.PokeTransfer
import com.carvalho.pokedex.model.pokemon.Pokemon


class AdapterListagem(
    private val pokemonItemClickListener: PokemonItemClickListener,
    var context: Context?
) : RecyclerView.Adapter<AdapterListagem.PokemonViewHolder>() {

    var pokemon = emptyList<PokeTransfer>()

    class PokemonViewHolder(var binding: CardviewPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(pokemon[position].sprites.front_default)
            .into(holder.binding.imPokemonCard)

        val color = PokemonSetBackgroudColor.setColor(pokemon[position].types[0].type.name)

        holder.binding.clBackground.setBackgroundColor(getColor(context!!, color))

        holder.binding.tvPokeomonNome.text =
            pokemon[position].name.replaceFirstChar { it.uppercase() }

        holder.itemView.setOnClickListener {
            pokemonItemClickListener.onPokemonClicked(pokemon[position])
        }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: List<PokeTransfer>) {
        pokemon = list.sortedBy {
            it.order
        }
        pokemon.distinct()
        notifyDataSetChanged()
    }

}

