package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.adapter.AdapterStatus
import com.carvalho.pokedex.adapter.AdapterTypes
import com.carvalho.pokedex.adapter.helpers.TypeClickListener
import com.carvalho.pokedex.databinding.FragmentDataBinding
import com.carvalho.pokedex.model.commonModel.FlavorText
import com.carvalho.pokedex.model.pokemon.Pokemon
import com.carvalho.pokedex.model.pokemon.stat.PokemonStat
import com.carvalho.pokedex.model.pokemon.type.PokemonType
import com.carvalho.pokedex.ui.fragments.dialog.MoveDialogFragment
import com.carvalho.pokedex.ui.fragments.dialog.TypeDialogFragment


class DataFragment : Fragment(), TypeClickListener {

    private lateinit var binding: FragmentDataBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var pokemonAdapterTypes: AdapterTypes
    private lateinit var pokemonAdapterStats: AdapterStatus

    private var listTypes: MutableList<PokemonType> = mutableListOf()
    private var listStats: MutableList<PokemonStat> = mutableListOf()

    private var pokemonSelec: Pokemon? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataBinding.inflate(layoutInflater, container, false)

        viewModel.responsePokemonSpecie.observe(viewLifecycleOwner) {
            setDescription(it.body()!!.flavor_text_entries.find { desc -> desc.language.name == "en" }?.flavor_text)
        }

        return binding.root
    }

    private fun recoverDataTypesStats() {
        listTypes.addAll(pokemonSelec!!.types)
        listStats.addAll(pokemonSelec!!.stats)
        viewModel.getSpecieByName(viewModel.pokeTransfer!!.name)
    }

    private fun setDescription(description: String?) {
        if (description != null) {
            binding.tvDescription.text = description.replace("\n", " ")
        }
    }

    private fun setHeightAndWeight() {
        if (pokemonSelec != null) {
            binding.tvHeight.text = "${pokemonSelec?.height?.div(10.0)} M"
            binding.tvWeight.text = "${pokemonSelec?.weight?.div(10.0)} Kg"
        }
    }

    private fun setupLayouts(
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager
    ) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
    }

    private fun setupLayoutStats() {
        setupLayouts(binding.rvStatus, LinearLayoutManager(context))
        getStats()
    }

    private fun setupLayoutTypes() {
        setupLayouts(binding.rvStatus, GridLayoutManager(context, listTypes.size))
        getTypes()
    }

    private fun getContents() {
        pokemonSelec = viewModel.pokemonSelec.value?.body()
    }

    private fun getStats() {
        if (::pokemonAdapterStats.isInitialized) {
            pokemonAdapterStats.setList(listStats)
        } else {
            pokemonAdapterStats = AdapterStatus(requireContext())
            binding.rvStatus.adapter = pokemonAdapterStats
            pokemonAdapterStats.setList(listStats)
        }
        for (i in 0..listStats.size) {
            AdapterStatus(requireContext()).setList(listStats)
        }
    }

    private fun getTypes() {
        if (::pokemonAdapterTypes.isInitialized) {
            pokemonAdapterTypes.setList(listTypes)
        } else {
            pokemonAdapterTypes = AdapterTypes(requireContext(), this)
            binding.rvTypes.adapter = pokemonAdapterTypes
            pokemonAdapterTypes.setList(listTypes)
        }
        for (i in 0..listTypes.size) {
            AdapterTypes(requireContext(), this).setList(listTypes)
        }
    }

    override fun onResume() {
        super.onResume()
        setProperHeightOfView()
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

    override fun onStart() {
        getContents()
        recoverDataTypesStats()

        setHeightAndWeight()
        setupLayoutTypes()
        setupLayoutStats()

        super.onStart()
    }

    override fun onTypeClicked(name: String) {
        viewModel.typeSelec = name
        val dialog = TypeDialogFragment(view?.width!!)
        dialog.show(parentFragmentManager, dialog.tag)
    }
}