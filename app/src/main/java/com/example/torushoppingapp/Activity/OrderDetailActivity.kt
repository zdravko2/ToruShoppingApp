package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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
        enableEdgeToEdge()
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

        viewModel.loadProductsFromOrder(order.id).observe(this, Observer { productOrderList ->
            if (productOrderList.isNullOrEmpty()) {
                binding.emptyOrderListText.visibility = View.VISIBLE
                binding.listView.visibility = View.GONE
                binding.orderIdText.visibility = View.GONE
                binding.totalPriceText.visibility = View.GONE
                binding.statusText.visibility = View.GONE

                Toast.makeText(this, "No products found for this order", Toast.LENGTH_SHORT).show()
            } else {

                binding.emptyOrderListText.visibility = View.GONE
                binding.listView.visibility = View.VISIBLE
                binding.orderIdText.visibility = View.VISIBLE
                binding.totalPriceText.visibility = View.VISIBLE
                binding.statusText.visibility = View.VISIBLE

                setupCartList(productOrderList)
            }
        })
    }

    private fun setupCartList(productOrderList: MutableList<Pair<ProductModel, OrderItem>>) {
        binding.apply {
            orderText.text = order.id
            orderIdText.text = order.id
            totalPriceText.text = "$" + order.totalPrice.toString()
            statusText.text = order.status

            listView.layoutManager = LinearLayoutManager(this@OrderDetailActivity, LinearLayoutManager.VERTICAL, false)
            listView.adapter = OrderProductAdapter(productOrderList)
        }
    }
}