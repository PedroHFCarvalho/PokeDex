package com.carvalho.pokedex.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.adapter.helpers.PokemonSetBackgroundColor
import com.carvalho.pokedex.databinding.CardviewPreviewBinding
import com.carvalho.pokedex.model.pokemon.PokeTransfer


class AdapterListagem(
    private val pokemonItemClickListener: PokemonItemClickListener,
    var context: Context?
) : RecyclerView.Adapter<AdapterListagem.PokemonViewHolder>() {

    var pokemon = mutableListOf<PokeTransfer>()

    class PokemonViewHolder(var binding: CardviewPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding =
            CardviewPreviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PokemonViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        holder.binding.tvPokeomonNome.text =
            pokemon[position].name.replaceFirstChar { it.uppercase() }
                .replace("-m", "♂")
                .replace("-f", "♀")

        Glide.with(holder.itemView.context)
            .load(pokemon[position].sprites.front_default)
            .into(holder.binding.imPokemonCard)

        val color = PokemonSetBackgroundColor.setColor(pokemon[position].types[0].type.name)
        holder.binding.cvPreview.setCardBackgroundColor(getColor(context!!, color))

        val order = pokemon[position].id
        when (pokemon[position].id.toString().length) {
            1 -> holder.binding.tvOrder.text = "#00$order"
            2 -> holder.binding.tvOrder.text = "#0$order"
            else -> holder.binding.tvOrder.text = "#$order"
        }

        holder.itemView.setOnClickListener {
            pokemonItemClickListener.onPokemonClicked(pokemon[position])
        }

    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun setList(list: List<PokeTransfer>) {
        val oldSize = pokemon.size
        pokemon.addAll(list)
        notifyItemRangeInserted(oldSize, pokemon.size)
    }

}

