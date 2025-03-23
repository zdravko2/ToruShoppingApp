package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.ProductListCategoryAdapter
import com.example.torushoppingapp.R
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductListBinding
    private val viewModel = MainViewModel()
    private var id:String = ""
    private var title:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        initList()
    }

    private fun getBundle() {
        id = intent.getStringExtra("id")!!
        title = intent.getStringExtra("title")!!

        binding.categoryText.text = title
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewModel.loadProductCategory(id).observe(this@ProductListActivity, Observer {
                listView.layoutManager = LinearLayoutManager(this@ProductListActivity,
                    LinearLayoutManager.VERTICAL, false)
                listView.adapter = ProductListCategoryAdapter(it)
                progressBar.visibility = View.GONE
            })
            backButton.setOnClickListener{
                finish()
            }
        }
    }
}