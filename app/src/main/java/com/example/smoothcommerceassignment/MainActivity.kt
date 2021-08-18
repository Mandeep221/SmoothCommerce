package com.example.smoothcommerceassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smoothcommerceassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ColoursViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecyclerView()

    }

    private fun configureRecyclerView() {

        val coloursAdapter = ColoursAdapter()

        val gridLayoutManager = GridLayoutManager(
            this@MainActivity,
            2,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.apply {
            recyclerView.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                adapter = coloursAdapter
            }
            viewModel.getColours().observe(this@MainActivity) { coloursList ->
                coloursAdapter.updateColoursList(coloursList)
            }
        }
    }
}