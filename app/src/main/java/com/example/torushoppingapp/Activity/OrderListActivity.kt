package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.OrderListAdapter
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityOrderBinding

class OrderListActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderBinding
    private var viewModel = MainViewModel()
    private var user_id:String = ""
    private var title:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()

        initButtons()
        getBundle()
        initList()
    }

    fun initButtons()
    {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun getBundle() {
        user_id = "user_1"
        title = "order"
        // TODO: get orders by logged-in user
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            emptyOrderListText.visibility = View.GONE
            viewModel.loadOrder(user_id).observe(this@OrderListActivity, Observer { orders ->
                if (orders.isEmpty()) {
                    emptyOrderListText.visibility = View.VISIBLE
                    listView.visibility = View.GONE
                } else {
                    listView.visibility = View.VISIBLE
                    listView.layoutManager = LinearLayoutManager(this@OrderListActivity,
                        LinearLayoutManager.VERTICAL, false)
                    listView.adapter = OrderListAdapter(orders)
                }
                progressBar.visibility = View.GONE
            })
        }
    }
}