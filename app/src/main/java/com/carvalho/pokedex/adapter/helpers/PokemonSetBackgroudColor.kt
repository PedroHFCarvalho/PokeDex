package com.carvalho.pokedex.adapter.helpers

import com.carvalho.pokedex.R
import com.carvalho.pokedex.model.pokemon.type.PokemonType

class PokemonSetBackgroudColor {

    companion object {
        fun setColor(type: String): Int {
            return when (type) {
                "bug" -> R.color.bug
                "dark" -> R.color.dark
                "dragon" -> R.color.dragon
                "electric" -> R.color.electric
                "fairy" -> R.color.fairy
                "fighting" -> R.color.fighting
                "fire" -> R.color.fire
                "flying" -> R.color.flying
                "ghost" -> R.color.ghost
                "grass" -> R.color.grass
                "ground" -> R.color.ground
                "ice" -> R.color.ice
                "normal" -> R.color.normal
                "poison" -> R.color.poison
                "psychic" -> R.color.psychic
                "rock" -> R.color.rock
                "steel" -> R.color.steel
                "water" -> R.color.water
                else -> R.color.white
            }

        }
    }

}