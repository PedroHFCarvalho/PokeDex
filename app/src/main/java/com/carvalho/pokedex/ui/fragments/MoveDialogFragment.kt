package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterTypes
import com.carvalho.pokedex.databinding.DialogMovesBinding
import com.carvalho.pokedex.model.moves.Move
import com.carvalho.pokedex.model.pokemon.type.PokemonType

class MoveDialogFragment(private val width: Int) : DialogFragment() {

    private lateinit var binding: DialogMovesBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var pokemonAdapterTypes: AdapterTypes
    private var listTypes: MutableList<PokemonType> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMovesBinding.inflate(layoutInflater, container, false)
        binding.clDialog.minWidth = width - 200

        viewModel.dataMove.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                val move = it.body()
                buildContentDialog(move!!)
                Log.d("move", move.toString())
            }
        }
        return binding.root
    }

    private fun recoverData() {
        if (viewModel.moveSelec != null) {
            viewModel.getDataMove(viewModel.moveSelec!!)
        }
    }

    private fun buildContentDialog(move: Move) {
        binding.tvDescriptionMove.text =
            move.flavorTextEntries.first().flavor_text
                .replace("\n", " ")
        binding.tvDescriptionEffect.text =
            move.effectEntries.first().effect
                .replace("\n", " ")
                .replace("\$effect_chance%", move.effectChance.toString())
                .replace("*", "\n*")
        binding.tvNameMove.text = move.name.replaceFirstChar { char -> char.uppercase() }
        binding.tvAccuracy.text = move.accuracy.toString()
        binding.tvPower.text = move.power.toString()
        binding.tvPP.text = move.pp.toString()
        binding.tvEffect.text = move.effectChance.toString()
        listTypes.add(PokemonType(0, move.type))
        setupLayoutTypes()
    }

    private fun setupLayoutTypes() {
        binding.rvTypesDialog.layoutManager = LinearLayoutManager(context)
        getTypes()
    }

    private fun getTypes() {
        if (::pokemonAdapterTypes.isInitialized) {
            pokemonAdapterTypes.setList(listTypes)
        } else {
            pokemonAdapterTypes = AdapterTypes(requireContext())
            binding.rvTypesDialog.adapter = pokemonAdapterTypes
            pokemonAdapterTypes.setList(listTypes)
        }
        for (i in 0..listTypes.size) {
            AdapterTypes(requireContext()).setList(listTypes)
        }
    }

    override fun onResume() {
        listTypes.clear()
        recoverData()
        super.onResume()
    }

}