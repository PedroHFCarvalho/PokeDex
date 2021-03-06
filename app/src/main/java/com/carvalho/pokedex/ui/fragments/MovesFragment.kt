package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterAbility
import com.carvalho.pokedex.adapter.AdapterMoves
import com.carvalho.pokedex.adapter.helpers.AbilityClickListener
import com.carvalho.pokedex.adapter.helpers.MovesClickListener
import com.carvalho.pokedex.databinding.FragmentMovesBinding
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.pokemon.ability.PokemonAbility
import com.carvalho.pokedex.model.pokemon.move.PokemonMove
import com.carvalho.pokedex.ui.fragments.dialog.AbilityDialogFragment
import com.carvalho.pokedex.ui.fragments.dialog.MoveDialogFragment

class MovesFragment : Fragment(), MovesClickListener, AbilityClickListener {

    private lateinit var binding: FragmentMovesBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var listAbility: MutableList<PokemonAbility> = mutableListOf()
    private var listMove: MutableList<PokemonMove> = mutableListOf()

    private lateinit var pokemonAdapterAbility: AdapterAbility
    private lateinit var pokemonAdapterMove: AdapterMoves

    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovesBinding.inflate(layoutInflater, container, false)

        getContentsForMoves()
        recoverDataAbilityAndMoves()

        setupLayoutAbility()
        setupLayoutMoves()

        return binding.root
    }

    private fun getContentsForMoves() {
        pokemonSelec = viewModel.pokemonSelec.value?.body()
    }

    private fun recoverDataAbilityAndMoves() {
        listAbility.addAll(pokemonSelec!!.abilities)
        listMove.addAll(pokemonSelec!!.moves)

    }

    private fun setupLayouts(
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager
    ) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
    }

    private fun setupLayoutMoves() {
        setupLayouts(binding.rvMoves, LinearLayoutManager(context))
        getPageMoves()
    }

    private fun setupLayoutAbility() {
        setupLayouts(binding.rvAbility, LinearLayoutManager(context))
        getPageAbility()
    }

    private fun getPageAbility() {
        if (::pokemonAdapterAbility.isInitialized) {
            pokemonAdapterAbility.setList(listAbility)
        } else {
            pokemonAdapterAbility = AdapterAbility(requireContext(), this)
            binding.rvAbility.adapter = pokemonAdapterAbility
            pokemonAdapterAbility.setList(listAbility)
        }
        for (i in 0..listAbility.size) {
            AdapterAbility(requireContext(), this).setList(listAbility)
        }
    }

    private fun getPageMoves() {
        if (::pokemonAdapterMove.isInitialized) {
            pokemonAdapterMove.setList(listMove)
        } else {
            pokemonAdapterMove = AdapterMoves(requireContext(), this)
            binding.rvMoves.adapter = pokemonAdapterMove
            pokemonAdapterMove.setList(listMove)
        }
        for (i in 0..listMove.size) {
            AdapterMoves(requireContext(), this).setList(listMove)
        }
    }

    private fun setProperHeightOfView() {
        val layoutView = view
        if (layoutView != null) {
            val layoutParams = layoutView.layoutParams
            if (layoutParams != null) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                layoutView.requestLayout()
            }
        }
    }

    override fun onMoveClicked(name: String) {
        viewModel.moveSelec = name
        val dialog = MoveDialogFragment(view?.width!!)
        dialog.show(parentFragmentManager, dialog.tag)

    }

    override fun onAbilityClicked(name: String) {
        viewModel.abilitySelec = name
        val dialog = AbilityDialogFragment(view?.width!!)
        dialog.show(parentFragmentManager, dialog.tag)

    }

    override fun onResume() {
        setProperHeightOfView()
        super.onResume()
    }
}
