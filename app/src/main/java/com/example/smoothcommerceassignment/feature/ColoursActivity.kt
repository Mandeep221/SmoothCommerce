package com.example.smoothcommerceassignment.feature

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smoothcommerceassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ColoursActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ColoursViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecyclerView()

        binding.btnSearch.setOnClickListener {
            val searchKey = binding.etColour.text.toString()

            if (searchKey.trim().isNotEmpty()) {
                if (searchKey.trim().length in 3..10) {
                    viewModel.handleSearchClick(binding.etColour.text.toString())
                    binding.apply {
                        progressView.visibility = View.VISIBLE
                        recyclerView.visibility = View.INVISIBLE
                    }
                } else {
                    Toast.makeText(
                        this@ColoursActivity,
                        "Search key length must be between 3 and 10 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@ColoursActivity,
                    "Search key must NOT be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            closeKeyboard()
        }
    }

    private fun configureRecyclerView() {

        val coloursAdapter = ColoursAdapter { imgUrl ->
            colourItemClicked(imgUrl)
        }

        val gridLayoutManager = GridLayoutManager(
            this@ColoursActivity,
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
            viewModel.getColours().observe(this@ColoursActivity) { coloursList ->
                coloursAdapter.updateColoursList(coloursList)
                binding.apply {
                    progressView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun colourItemClicked(imgUrl: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(imgUrl)
            )
        )
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}