package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.AdapterSearch
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.databinding.FragmentSearchBinding
import com.carvalho.pokedex.model.pokemon.PokeTransfer
import com.carvalho.pokedex.model.pokemon.Pokemon

class SearchFragment : Fragment(), PokemonItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var pokemonAdapter: AdapterSearch
    private lateinit var layoutManager: LinearLayoutManager

    private var isLoading = false
    private var list: MutableSet<PokeTransfer> = mutableSetOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        isLoadingFalse()
        setupLayout()

        viewModel.responsePokemonSearch.observe(viewLifecycleOwner) {
            list.add(buildPokeTransfer(it.body()!!))
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                list.clear()
                if (!query.isNullOrBlank()) {
                    getContents(query)
                    includeContentsInPage()
                } else {
                    Toast.makeText(context, "Nada foi digítado", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        return binding.root
    }

    private fun buildPokeTransfer(pokemon: Pokemon): PokeTransfer {
        return PokeTransfer(
            pokemon.id,
            pokemon.name,
            pokemon.order,
            pokemon.sprites,
            pokemon.types
        )
    }

    private fun isLoadingTrue() {
        isLoading = true
        binding.inLoadingSearch.pbPaginationList.visibility = View.INVISIBLE
    }

    private fun isLoadingFalse() {
        isLoading = false
        binding.inLoadingSearch.pbPaginationList.visibility = View.GONE
    }

    private fun setupLayout() {
        binding.rvSearches.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.rvSearches.layoutManager = layoutManager
    }

    private fun getContents(query: String) {
        viewModel.getPokemonByName(query)
    }

    private fun includeContentsInPage() {
        isLoadingTrue()
        Handler(Looper.myLooper() ?: return).postDelayed({

            if (::pokemonAdapter.isInitialized) {
                if (binding.rvSearches.adapter == null) {
                    setAdapter()
                    setListInAdapter()
                    notFound()
                } else {
                    setListInAdapter()
                }
            } else {
                setAdapter()
                setListInAdapter()
                notFound()
            }
            isLoadingFalse()
        }, 500)
    }

    private fun notFound() {
        if (list.isEmpty()) {
            Toast.makeText(context, "Não foi encontrado nada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter() {
        pokemonAdapter = AdapterSearch(this)
        binding.rvSearches.adapter = pokemonAdapter
    }

    private fun setListInAdapter() {
        pokemonAdapter.setList(list.sortedBy { it.name })
    }

    override fun onPokemonClicked(pokemon: PokeTransfer) {
        viewModel.pokeTransfer = pokemon
        findNavController().navigate(R.id.action_searchFragment_to_pokemonFragment)
    }

}
