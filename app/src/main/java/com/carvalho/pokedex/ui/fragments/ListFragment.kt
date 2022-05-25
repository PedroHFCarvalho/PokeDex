package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.pokedex.MainViewModel
import com.carvalho.pokedex.R
import com.carvalho.pokedex.adapter.AdapterListagem
import com.carvalho.pokedex.adapter.helpers.PokemonItemClickListener
import com.carvalho.pokedex.databinding.FragmentListBinding
import com.carvalho.pokedex.model.pokemon.PokeTransfer
import com.carvalho.pokedex.model.pokemon.Pokemon


class ListFragment : Fragment(), PokemonItemClickListener {

    private lateinit var binding: FragmentListBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var page: Int = 0
    private var isLoading = false
    private var limite = 25
    private var list: MutableList<PokeTransfer> = mutableListOf()

    private lateinit var pokemonAdapter: AdapterListagem
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        viewModel.responsePokemon.observe(viewLifecycleOwner) {
            val pokeTransfer = buildPokeTransfer(it.body()!!)
            if (!list.contains(pokeTransfer)) {
                list.add(pokeTransfer)
            }
        }

        binding.rvListPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    getNextPage()
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            private fun getNextPage() {
                val visibleItemCont = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = pokemonAdapter.itemCount

                if (!isLoading) {
                    if ((visibleItemCont + pastVisibleItem) >= total) {
                        page++
                        Log.d("Cont Page", page.toString())
                        getContentsForList()
                        includeContentsInPage()
                    }
                }
            }
        })
        return binding.root
    }

    private fun includeContentsInPage() {
        isLoadingTrue()
        Handler(Looper.getMainLooper()).postDelayed({
            if (::pokemonAdapter.isInitialized) {
                if (binding.rvListPokemon.adapter == null) {
                    setAdapter()
                    setListInAdapter()
                } else {
                    setListInAdapter()
                }
            } else {
                setAdapter()
                setListInAdapter()
            }
            isLoadingFalse()
        }, 1500)
    }

    private fun setAdapter() {
        pokemonAdapter = AdapterListagem(this, context)
        binding.rvListPokemon.adapter = pokemonAdapter
    }

    private fun setListInAdapter() {
        pokemonAdapter.setList(list.sortedBy { it.order })
        list.clear()
    }

    private fun setupLayoutList() {
        binding.rvListPokemon.setHasFixedSize(true)
        layoutManager = GridLayoutManager(context, 3)
        binding.rvListPokemon.layoutManager = layoutManager
    }

    private fun getContentsForList() {
        val start = ((page) * limite) + 1
        val end = (page + 1) * limite

        for (i in start..end) {
            viewModel.getPokemonByNumber(i)
            Log.d("Cont", i.toString())
        }
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
        binding.inLoadingList.pbPaginationList.visibility = View.VISIBLE
    }

    private fun isLoadingFalse() {
        isLoading = false
        binding.inLoadingList.pbPaginationList.visibility = View.GONE
    }

    override fun onPokemonClicked(pokemon: PokeTransfer) {
        viewModel.pokeTransfer = pokemon
        findNavController().navigate(
            R.id.action_listFragment_to_pokemonFragment
        )
    }

    override fun onResume() {
        page = 0
        setupLayoutList()
        getContentsForList()
        includeContentsInPage()

        super.onResume()
    }
}

