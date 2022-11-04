package com.stas.easyfood.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stas.easyfood.R
import com.stas.easyfood.db.MealDatabase
import com.stas.easyfood.viewModel.HomeViewModel
import com.stas.easyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProvider = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProvider)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigator = findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigator,navController)

    }
}