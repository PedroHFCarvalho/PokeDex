package com.carvalho.pokedex.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.carvalho.pokedex.ui.fragments.DataFragment
import com.carvalho.pokedex.ui.fragments.EvolutionFragment
import com.carvalho.pokedex.ui.fragments.MovesFragment
import com.carvalho.pokedex.ui.fragments.PokemonFragment

class AdapterAppPager(fragmentActivity: PokemonFragment): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->DataFragment()
            1->MovesFragment()
            2->EvolutionFragment()
            else -> DataFragment()
        }
    }
}