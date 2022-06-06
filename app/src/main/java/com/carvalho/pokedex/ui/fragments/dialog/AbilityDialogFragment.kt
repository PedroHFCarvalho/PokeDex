package com.carvalho.pokedex.ui.fragments.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.databinding.DialogAbilityBinding
import com.carvalho.pokedex.model.ability.Ability

class AbilityDialogFragment(private val width: Int) : DialogFragment() {

    private lateinit var binding: DialogAbilityBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var ability: Ability

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAbilityBinding.inflate(layoutInflater, container, false)
        binding.clDialogAbility.minWidth = width - 100

        viewModel.dataAbility.observe(viewLifecycleOwner) {
            if (it.body() != null) {
                ability = it.body()!!
                buildContentDialog(ability)
            }
        }

        return binding.root
    }

    private fun recoverData() {
        val abilityString = viewModel.abilitySelec
        if (abilityString != null) {
            getDataAbility(abilityString)
        }
    }

    private fun getDataAbility(name: String) {
        viewModel.getDataAbility(name)
    }

    private fun buildContentDialog(ability: Ability) {
        binding.tvNameAbility.text = ability.name.replaceFirstChar { it.uppercase() }
        binding.tvDescriptionAbility.text =
            ability.flavorTextEntries.find { it.language.name == "en" }?.flavor_text
        binding.tvDescriptionEffect.text =
            ability.effectEntries.find { it.language.name == "en" }?.effect

        val pokemonAbility =
            ability.pokemon.find { it.pokemon.name == viewModel.pokemonSelec.value?.body()?.name }

        if (pokemonAbility != null) {
            binding.tvSlot.text = pokemonAbility.slot.toString()
            binding.rbHiddenAbility.isChecked = pokemonAbility.is_hidden
        }
    }

    private fun clearContent() {
        binding.tvNameAbility.text = " "
        binding.tvDescriptionAbility.text = " "
        binding.tvDescriptionEffect.text = " "
    }

    override fun onResume() {
        clearContent()
        recoverData()
        super.onResume()
    }
}