package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.AdapterSearch
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.databinding.FragmentSearchBinding
import com.carvalho.pokedex.model.pokemon.Pokemon

class SearchFragment : Fragment(), PokemonItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var pokemonAdapter: AdapterSearch
    private lateinit var layoutManager: LinearLayoutManager

    private var isLoading = false
    private var list: MutableList<Pokemon> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        setupPokemonList()

        viewModel.responsePokemonSearch.observe({ lifecycle }) {
            list.addAll(listOf(it.body()!!))
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                list.clear()
                if (!query.isNullOrBlank()) {
                    getPage(query)
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


    private fun setupPokemonList() {
        binding.rvSearches.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.rvSearches.layoutManager = layoutManager
        binding.inLoadingSearch.pbPaginationList.visibility = View.GONE
    }

    private fun getPage(query: String) {
        isLoading = true
        binding.inLoadingSearch.pbPaginationList.visibility = View.VISIBLE

        viewModel.getPokemonByName(query)


        Handler(Looper.myLooper() ?: return).postDelayed({
            if (::pokemonAdapter.isInitialized) {
                pokemonAdapter.setList(list)
                if (list.isEmpty()) {
                    Toast.makeText(context, "Não foi encontrado nada", Toast.LENGTH_SHORT).show()
                }
                Log.v("Sucess", list.toString())
            } else {
                pokemonAdapter = AdapterSearch(this)
                binding.rvSearches.adapter = pokemonAdapter
                pokemonAdapter.setList(list)
                if (list.isEmpty()) {
                    Toast.makeText(context, "Não foi encontrado nada", Toast.LENGTH_SHORT).show()
                }
            }
            isLoading = false
            binding.inLoadingSearch.pbPaginationList.visibility = View.GONE
        }, 2000)

    }

    override fun onPokemonClicked(pokemon: Pokemon) {
        viewModel.pokemonSelec = pokemon
        findNavController().navigate(R.id.action_searchFragment_to_pokemonFragment)
    }
}
