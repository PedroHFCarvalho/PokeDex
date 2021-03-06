package com.carvalho.pokedex.ui.fragments.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterTypes
import com.carvalho.pokedex.adapter.helpers.TypeClickListener
import com.carvalho.pokedex.databinding.DialogMovesBinding
import com.carvalho.pokedex.model.moves.Move
import com.carvalho.pokedex.model.pokemon.type.PokemonType

class MoveDialogFragment(private val width: Int) : DialogFragment(), TypeClickListener {

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
        binding.clDialog.minWidth = width - 100

        viewModel.dataMove.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                val move = it.body()
                buildContentDialog(move!!)
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
            move.effectEntries.find { it.language.name == "en" }!!.effect
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
            pokemonAdapterTypes = AdapterTypes(requireContext(), this)
            binding.rvTypesDialog.adapter = pokemonAdapterTypes
            pokemonAdapterTypes.setList(listTypes)
        }
        for (i in 0..listTypes.size) {
            AdapterTypes(requireContext(), this).setList(listTypes)
        }
    }


    private fun clearContent() {
        binding.tvDescriptionMove.text = " "
        binding.tvDescriptionEffect.text = " "
        binding.tvDescriptionEffect.text = " "
        binding.tvNameMove.text = " "
        binding.tvAccuracy.text = " "
        binding.tvPower.text = " "
        binding.tvPP.text = " "
        binding.tvEffect.text = " "
    }

    override fun onResume() {
        listTypes.clear()
        clearContent()
        recoverData()
        super.onResume()
    }

    override fun onTypeClicked(name: String) {
        viewModel.typeSelec = name
        val dialog = TypeDialogFragment(width)
        dialog.show(parentFragmentManager, dialog.tag)
    }

}