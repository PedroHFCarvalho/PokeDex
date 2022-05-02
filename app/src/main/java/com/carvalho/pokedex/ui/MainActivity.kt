package com.carvalho.pokedex.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
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

        setNavController()
        setupBottomNavigation()
        setupToolbar()

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_list -> {
                    val navOption = NavOptions.Builder()
                        .setEnterAnim(R.anim.from_left)
                        .setExitAnim(R.anim.to_right)
                        .setPopEnterAnim(R.anim.from_right)
                        .setPopExitAnim(R.anim.to_left)
                        .setPopUpTo(R.id.searchFragment, true)
                        .build()
                    findNavController(R.id.fvApresentation).navigate(
                        R.id.listFragment,
                        null,
                        navOption
                    )
                    true
                }
                R.id.fragment_search -> {
                    val navOption = NavOptions.Builder()
                        .setEnterAnim(R.anim.from_right)
                        .setExitAnim(R.anim.to_left)
                        .setPopEnterAnim(R.anim.from_left)
                        .setPopExitAnim(R.anim.to_right)
                        .setPopUpTo(R.id.listFragment, true)
                        .build()
                    findNavController(R.id.fvApresentation).navigate(
                        R.id.searchFragment,
                        null,
                        navOption,
                    )
                    true
                }
//                R.id.fragment_menu ->
//                    // do something here
//                    true
                else -> true
            }
        }

    }

    private fun setupBottomNavigation() {
        navigationView = binding.inNavigationBottom.nvBottom
        navigationView.selectedItemId = R.id.listFragment

        binding.inNavigationBottom.nvBottom.setupWithNavController(navController)
    }

    private fun setNavController() {
        navController = findNavController(R.id.fvApresentation)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.inToolbar.toolbar)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}