package com.carvalho.pokedex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.carvalho.pokedex.R
import com.carvalho.pokedex.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupBottomNavigation()

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_list -> {
                    findNavController(R.id.fragmentContainerView).navigate(
                        R.id.listFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.searchFragment, true)
                            .build()
                    )
                    true
                }
                R.id.fragment_search -> {
                    findNavController(R.id.fragmentContainerView).navigate(
                        R.id.searchFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.listFragment, true)
                            .build()
                    )
                    true
                }
                R.id.fragment_menu ->
                    // do something here
                    true
                else -> true
            }
        }

    }

    private fun setupBottomNavigation() {
        navigationView = binding.inNavigationBottom.nvBottom
        navigationView.selectedItemId = R.id.listFragment

        navController =
            findNavController(R.id.fragmentContainerView) // Identifica o navController com base no fragmenteView
        binding.inNavigationBottom.nvBottom.setupWithNavController(navController)// Set o NavController

    }


}