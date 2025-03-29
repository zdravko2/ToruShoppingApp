package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.ProductListCategoryAdapter
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductListBinding
    private val viewModel = MainViewModel()
    private var id:String = ""
    private var title:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
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
        id = intent.getStringExtra("id")!!
        title = intent.getStringExtra("title")!!

        binding.categoryText.text = title
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            emptyListText.visibility = View.GONE
            viewModel.loadProductCategory(id).observe(this@ProductListActivity, Observer { productList ->
                if (productList.isNullOrEmpty()) {
                    emptyListText.visibility = View.VISIBLE
                    listView.visibility = View.GONE
                } else {
                    listView.visibility = View.VISIBLE
                    listView.layoutManager = LinearLayoutManager(this@ProductListActivity,
                        LinearLayoutManager.VERTICAL, false)
                    listView.adapter = ProductListCategoryAdapter(productList)
                }
                progressBar.visibility = View.GONE
            })
        }
    }
}