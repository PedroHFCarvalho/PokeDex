package com.carvalho.pokedex.ui.fragments.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterTypes
import com.carvalho.pokedex.adapter.helpers.TypeClickListener
import com.carvalho.pokedex.databinding.DialogTypeBinding
import com.carvalho.pokedex.model.commonModel.NamedAPIResource
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.carvalho.pokedex.model.type.Types

class TypeDialogFragment(private val width: Int) : DialogFragment(), TypeClickListener {

    private lateinit var binding: DialogTypeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTypeBinding.inflate(layoutInflater, container, false)
        binding.clDialogType.minWidth = width - 100

        viewModel.dataType.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                val type = it.body()
                Log.d("type", it.body()!!.name.toString())
                buildContentDialog(type!!)
            }
        }

        return binding.root
    }

    private fun buildContentDialog(type: Types) {
        binding.tvNameTypeDialog.text = type.name.replaceFirstChar { char -> char.uppercase() }

        setupLayoutTypes(
            binding.rvTypesDialog,
            listOf(PokemonType(0, NamedAPIResource(type.name, "")))
        )

        setupLayoutTypes(binding.rvTypesNoDamageTo, buildList(type.damageRelations.noDamageTo))
        setupLayoutTypes(binding.rvTypesNoDamageFrom, buildList(type.damageRelations.noDamageFrom))

        setupLayoutTypes(binding.rvTypesHalfDamageTo, buildList(type.damageRelations.halfDamageTo))
        setupLayoutTypes(
            binding.rvTypesHalfDamageFrom,
            buildList(type.damageRelations.halfDamageFrom)
        )

        setupLayoutTypes(
            binding.rvTypesDoubleDamageTo,
            buildList(type.damageRelations.doubleDamageTo)
        )
        setupLayoutTypes(
            binding.rvTypesDoubleDamageFrom,
            buildList(type.damageRelations.doubleDamageFrom)
        )

    }

    private fun buildList(list: List<NamedAPIResource>): MutableList<PokemonType> {
        val support: MutableList<PokemonType> = mutableListOf()

        list.forEach {
            support.add(PokemonType(0, NamedAPIResource(it.name, "")))
        }
        return support
    }

    private fun setupLayoutTypes(rv: RecyclerView, list: List<PokemonType?>) {
        rv.layoutManager = GridLayoutManager(requireContext(), 3)
        getTypes(rv, list)
    }

    private fun getTypes(rv: RecyclerView, list: List<PokemonType?>) {
        val adapter = AdapterTypes(requireContext(), this)
        rv.adapter = adapter
        adapter.setList(list as MutableList<PokemonType>)
    }

    private fun recoverData() {
        if (viewModel.typeSelec != null) {
            viewModel.getDataType(viewModel.typeSelec!!)
        }
    }

    private fun clearContent() {
        binding.tvNameTypeDialog.text = " "
    }

    override fun onResume() {
        clearContent()
        recoverData()

        super.onResume()
    }

    override fun onTypeClicked(name: String) {
        viewModel.typeSelec = name
        recoverData()
    }

    override fun onPause() {
        viewModel.typeSelec = null
        super.onPause()
    }

}