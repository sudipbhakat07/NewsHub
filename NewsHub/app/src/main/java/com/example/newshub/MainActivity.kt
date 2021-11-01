package com.example.newshub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newshub.databinding.ActivityMainBinding
import com.example.newshub.presentation.ViewModel.NewsViewModel
import com.example.newshub.presentation.ViewModel.NewsViewModelFactory
import com.example.newshub.presentation.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel
    @Inject
    lateinit var newsAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsAdapter = NewsAdapter()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvMenu.setupWithNavController(
            navController
        )
        viewModel = ViewModelProvider(this,viewModelFactory)
            .get(NewsViewModel::class.java)


    }
}
