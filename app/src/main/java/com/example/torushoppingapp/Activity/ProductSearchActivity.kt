package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.ProductListAdapter
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityProductSearchBinding

class ProductSearchActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductSearchBinding
    private val viewModel = MainViewModel()
    private var query:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        getBundle()
        initList()
    }

    private fun initButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun getBundle() {
        query = intent.getStringExtra("query")!!
        binding.searchText.text = "\"$query\""
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            emptyListText.visibility = View.GONE
            viewModel.searchProducts(query).observe(this@ProductSearchActivity, Observer { productList ->
                if (productList.isNullOrEmpty()) {
                    emptyListText.visibility = View.VISIBLE
                    listView.visibility = View.GONE
                } else {
                    listView.visibility = View.VISIBLE
                    listView.layoutManager = LinearLayoutManager(this@ProductSearchActivity,
                        LinearLayoutManager.VERTICAL, false)
                    listView.adapter = ProductListAdapter(productList)
                }
                progressBar.visibility = View.GONE
            })
        }
    }
}