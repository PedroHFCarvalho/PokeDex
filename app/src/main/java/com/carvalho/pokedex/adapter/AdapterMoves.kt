package com.carvalho.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.adapter.helpers.MovesClickListener
import com.carvalho.pokedex.databinding.CardviewMovesBinding
import com.carvalho.pokedex.model.pokemon.move.PokemonMove

class AdapterMoves(val context: Context, private val movesClickListener: MovesClickListener) :
    RecyclerView.Adapter<AdapterMoves.PokemonViewHolder>() {

    var pokemon = emptyList<PokemonMove>()

    class PokemonViewHolder(var binding: CardviewMovesBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewMovesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.binding.tvMoves.text =
            pokemon[position].move.name.replaceFirstChar { it.uppercase() }

        holder.itemView.setOnClickListener {
            movesClickListener.onMoveClicked(pokemon[position].move.name)
        }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: MutableList<PokemonMove>) {
        pokemon = list
        notifyDataSetChanged()
    }


}