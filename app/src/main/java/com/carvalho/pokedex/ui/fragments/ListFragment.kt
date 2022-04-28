package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.AdapterListagem
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.databinding.FragmentListBinding

import com.carvalho.pokedex.model.pokemon.Pokemon


class ListFragment : Fragment(), PokemonItemClickListener {

    private lateinit var binding: FragmentListBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var page = 0
    private var isLoading = false
    private var limite = 25

    private lateinit var pokemonAdapter: AdapterListagem
    private lateinit var layoutManager: GridLayoutManager
    private var list: MutableSet<Pokemon> = mutableSetOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        setupPokemonList()

        viewModel.responsePokemon.observe({ lifecycle }) {
            list.add(it.body()!!)
        }

        binding.rvListPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visivleItemCont = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = pokemonAdapter.itemCount


                    if (!isLoading) {
                        if ((visivleItemCont + pastVisibleItem) >= total) {
                            page++
                            getPage()
                            Log.d("Final", page.toString())
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        return binding.root
    }


    private fun setupPokemonList() {
        binding.rvListPokemon.setHasFixedSize(true)
        layoutManager = GridLayoutManager(context, 3)
        binding.rvListPokemon.layoutManager = layoutManager


        getPage()
    }

    private fun getPage() {
        isLoading = true
        binding.inLoadingList.pbPaginationList.visibility = View.VISIBLE
        val start = ((page) * limite) + 1
        val end = (page + 1) * limite

        for (i in start..end) {
            viewModel.getPokemonByNumber(i)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (::pokemonAdapter.isInitialized) {
                if (binding.rvListPokemon.adapter == null) {
                    pokemonAdapter = AdapterListagem(this, context)
                    binding.rvListPokemon.adapter = pokemonAdapter
                    pokemonAdapter.setList(list.distinctBy { it.name })
                    Log.e("true", "true")

                } else {

                    pokemonAdapter.setList(list.distinctBy { it.name })
                    Log.v("Pag1", list.toString())

                }
            } else {
                pokemonAdapter = AdapterListagem(this, context)
                binding.rvListPokemon.adapter = pokemonAdapter
                pokemonAdapter.setList(list.distinctBy { it.name })
                Log.v("Pag2", list.toString())
            }
            isLoading = false
            binding.inLoadingList.pbPaginationList.visibility = View.GONE


        }, 2000)

    }

    override fun onPokemonClicked(pokemon: Pokemon) {
        viewModel.pokemonSelec = pokemon
        findNavController().navigate(R.id.action_listFragment_to_pokemonFragment)

    }

}

