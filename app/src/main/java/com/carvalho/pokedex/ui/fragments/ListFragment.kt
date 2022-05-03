package com.carvalho.pokedex.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
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

    private var page = 0
    private var isLoading = false
    private var limite = 25
    private var list: MutableSet<PokeTransfer> = mutableSetOf()

    private lateinit var pokemonAdapter: AdapterListagem
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)

        setupLayoutList()
        getContentsForList()
        includeContentsInPage()

        viewModel.responsePokemon.observe(viewLifecycleOwner) {
            list.add(buildPokeTransfer(it.body()!!))
        }

        binding.rvListPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    getNextPage()
                }
                super.onScrolled(recyclerView, dx, dy)
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
        }, 2000)
    }

    private fun setAdapter() {
        pokemonAdapter = AdapterListagem(this, context)
        binding.rvListPokemon.adapter = pokemonAdapter
    }

    private fun setListInAdapter() {
        pokemonAdapter.setList(list.toList())
    }

    private fun setupLayoutList() {
        binding.rvListPokemon.setHasFixedSize(true)
        layoutManager = GridLayoutManager(context, 3)
        binding.rvListPokemon.layoutManager = layoutManager
    }

    private fun getNextPage() {
        val visibleItemCont = layoutManager.childCount
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val total = pokemonAdapter.itemCount

        if (!isLoading) {
            if ((visibleItemCont + pastVisibleItem) >= total) {
                page++
                getContentsForList()
                includeContentsInPage()
            }
        }
    }

    private fun getContentsForList() {
        val start = ((page) * limite) + 1
        val end = (page + 1) * limite

        for (i in start..end) {
            viewModel.getPokemonByNumber(i)
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
}

