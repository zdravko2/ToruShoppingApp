package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Activity.CartActivity
import com.example.torushoppingapp.Activity.ProductSearchActivity
import com.example.torushoppingapp.Adapter.CartAdapter
import com.example.torushoppingapp.Adapter.FavoriteListAdapter
import com.example.torushoppingapp.Adapter.ProductListAdapter
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.R
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityFavoritesListBinding

class FavoritesListActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoritesListBinding
    private val viewModel = MainViewModel()
    private var userId:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        getBundle()
        initList()
    }

    private fun initButtons() {
        binding.apply {
            backButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun getBundle() {
        userId = SessionManager.getUserId(this).toString()
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            emptyListText.visibility = View.GONE
            viewModel.loadFavorites(userId).observe(this@FavoritesListActivity, Observer { productFavoritesList ->
                if (productFavoritesList.isNullOrEmpty()) {
                    emptyListText.visibility = View.VISIBLE
                    listView.visibility = View.GONE
                } else {
                    listView.visibility = View.VISIBLE

                    listView.layoutManager = LinearLayoutManager(this@FavoritesListActivity, LinearLayoutManager.VERTICAL, false)
                    listView.adapter = FavoriteListAdapter(productFavoritesList)
                }
                progressBar.visibility = View.GONE
            })
        }
    }
}