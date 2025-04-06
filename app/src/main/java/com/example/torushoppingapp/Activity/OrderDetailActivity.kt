package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.OrderProductAdapter
import com.example.torushoppingapp.Domain.OrderItem
import com.example.torushoppingapp.Domain.OrderModel
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var order: OrderModel
    private var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        loadOrderDetails()
    }

    private fun initButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadOrderDetails() {
        order = intent.getSerializableExtra("object") as OrderModel

        binding.apply {
            emptyOrderListText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.loadProductsFromOrder(order.id).observe(this@OrderDetailActivity, Observer { productOrderList ->
                if (productOrderList.isNullOrEmpty()) {
                    emptyOrderListText.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    listView.visibility = View.GONE
                    orderIdText.visibility = View.GONE
                    totalPriceText.visibility = View.GONE
                    statusText.visibility = View.GONE
                } else {

                    emptyOrderListText.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    listView.visibility = View.VISIBLE
                    orderIdText.visibility = View.VISIBLE
                    totalPriceText.visibility = View.VISIBLE
                    statusText.visibility = View.VISIBLE

                    setupCartList(productOrderList)
                }
            })
        }
    }

    private fun setupCartList(productOrderList: MutableList<Pair<ProductModel, OrderItem>>) {
        binding.apply {
            orderText.text = order.id
            orderIdText.text = order.id
            totalPriceText.text = "$" + String.format("%.2f", order.totalPrice)
            statusText.text = order.status

            listView.layoutManager = LinearLayoutManager(this@OrderDetailActivity, LinearLayoutManager.VERTICAL, false)
            listView.adapter = OrderProductAdapter(productOrderList)
        }
    }
}