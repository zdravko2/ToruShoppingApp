package com.example.torushoppingapp.Activity

import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.CartAdapter
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Helper.CustomNotificationManager
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel = MainViewModel()
    private var userId: String = ""

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        getBundle()
        observeCart()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun initButtons() {
        binding.apply {
            backButton.setOnClickListener {
                finish()
            }

            placeOrderButton.setOnClickListener {
                val userId = SessionManager.getUserId(this@CartActivity).toString()

                viewModel.placeOrder(userId).observe(this@CartActivity) { success ->
                    if (success) {
                        notifyUser()

                        startActivity(Intent(this@CartActivity, OrderListActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@CartActivity, "Failed to place order. Check product availability.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun getBundle() {
        userId = SessionManager.getUserId(this).toString()
    }

    private fun observeCart() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            emptyCartText.visibility = View.GONE
            viewModel.loadProductsFromCart(userId).observe(this@CartActivity, Observer { productCartList ->
                if (productCartList.isNullOrEmpty()) {
                    emptyCartText.visibility = View.VISIBLE
                    listView.visibility = View.GONE
                    totalFeeText.visibility = View.GONE
                    deliveryText.visibility = View.GONE
                    totalTaxText.visibility = View.GONE
                } else {
                    listView.visibility = View.VISIBLE
                    totalFeeText.visibility = View.VISIBLE
                    deliveryText.visibility = View.VISIBLE
                    totalTaxText.visibility = View.VISIBLE

                    listView.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
                    listView.adapter = CartAdapter(productCartList)

                    calculateCart(productCartList)
                }
                progressBar.visibility = View.GONE
            })
        }
    }

    private fun calculateCart(productCartList: MutableList<Pair<ProductModel, CartItem>>) {
        val percentTax = 0.02
        val deliveryFee = 10.0

        val productTotal = productCartList.sumOf { (product, cartItem) ->
            product.price * cartItem.quantity
        }
        val tax = Math.round(productTotal * percentTax * 100.0) / 100.0
        val total = Math.round((productTotal + tax + deliveryFee) * 100.0) / 100.0

        binding.totalFeeText.text = String.format("$%.2f", productTotal)
        binding.totalTaxText.text = String.format("$%.2f", tax)
        binding.deliveryText.text = String.format("$%.2f", deliveryFee)
        binding.priceText.text = String.format("$%.2f", total)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun notifyUser(title: String = "Order Confirmed", message: String = "Thank you for your order!", delay: Long = 10000)
    {
        CustomNotificationManager(this).scheduleNotification(
            channelId = "order_channel",
            notificationId = 1,
            title = title,
            message = message,
            delay = delay
        )
    }
}