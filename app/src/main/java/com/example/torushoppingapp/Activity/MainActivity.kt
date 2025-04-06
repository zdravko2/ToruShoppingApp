package com.example.torushoppingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Adapter.CategoryAdapter
import com.example.torushoppingapp.Adapter.PopularAdapter
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityMainBinding
import com.google.android.material.search.SearchBar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        initBanner()
        initCategory()
        initPopular()
    }

    var darkMode = false
    private fun initButtons()
    {
        binding.apply {
            settingsButton.setOnClickListener {
                if (darkMode)
                {
                    darkMode = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else
                {
                    darkMode = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }


            searchBar.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                    val query = searchBar.text.toString().trim()
                    if (query.isNotEmpty()) {
                        val intent = Intent(this@MainActivity, ProductSearchActivity::class.java)
                        intent.putExtra("query", query)
                        startActivity(intent)
                    }
                    true
                } else {
                    false
                }
            }

            exploreButton.setOnClickListener {

            }

            cartButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, CartActivity::class.java))
            }

            favoritesButton.setOnClickListener {

            }

            ordersButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, OrderListActivity::class.java))
            }

            profileButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.loadBanner().observeForever {
            Glide.with(this@MainActivity)
                .load(it[0].url)
                .into(binding.banner)
            binding.progressBarBanner.visibility = View.GONE
        }
        viewModel.loadBanner()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.loadCategory().observeForever {
            binding.recyclerViewCategory.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        }
        viewModel.loadCategory()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observeForever {
            binding.recyclerViewPopular.layoutManager =
                GridLayoutManager(this, 2)
            binding.recyclerViewPopular.adapter = PopularAdapter(it)
            binding.progressBarPopular.visibility = View.GONE
        }
        viewModel.loadPopular()
    }
}