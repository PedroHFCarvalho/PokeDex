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
import com.carvalho.pokedex.adapter.AdapterListagem
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
    private lateinit var pokemon: Pokemon
    private lateinit var pokeTransfer: PokeTransfer
    private var list: MutableSet<PokeTransfer> = mutableSetOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        setupPokemonList()

        viewModel.responsePokemonSearch.observe(viewLifecycleOwner) {
            pokemon = it.body()!!
            pokeTransfer = PokeTransfer(
                pokemon.id,
                pokemon.name,
                pokemon.order,
                pokemon.sprites,
                pokemon.types
            )
            list.add(pokeTransfer)
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
                if (binding.rvSearches.adapter == null) {
                    pokemonAdapter = AdapterSearch(this)
                    binding.rvSearches.adapter = pokemonAdapter
                    pokemonAdapter.setList(list.sortedBy { it.name })
                    if (list.isEmpty()) {
                        Toast.makeText(context, "Não foi encontrado nada", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Log.e("true", "true")

                } else {

                    pokemonAdapter.setList(list.sortedBy { it.name })
                    Log.v("Pag1", list.toString())

                }
            } else {
                pokemonAdapter = AdapterSearch(this)
                binding.rvSearches.adapter = pokemonAdapter
                pokemonAdapter.setList(list.distinctBy { it.name })
                if (list.isEmpty()) {
                    Toast.makeText(context, "Não foi encontrado nada", Toast.LENGTH_SHORT).show()
                }
                Log.v("Pag2", list.toString())
            }
            isLoading = false
            binding.inLoadingSearch.pbPaginationList.visibility = View.GONE
        }, 1000)

    }

    override fun onPokemonClicked(pokemon: PokeTransfer) {
        viewModel.pokeTransfer = pokemon
        findNavController().navigate(R.id.action_searchFragment_to_pokemonFragment)
    }
}
