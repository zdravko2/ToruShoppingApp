package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.CartAdapter
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        observeCart()
    }

    private fun initButtons() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeCart() {
        val userId = "user_1" // Replace with your dynamic user ID
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

        // Sum up (price * quantity) for all products in the cart
        val productTotal = productCartList.sumOf { (product, cartItem) ->
            product.price * cartItem.quantity
        }
        val tax = Math.round(productTotal * percentTax * 100.0) / 100.0
        val total = Math.round((productTotal + tax + deliveryFee) * 100.0) / 100.0

        binding.totalFeeText.text = "$$productTotal"
        binding.totalTaxText.text = "$$tax"
        binding.deliveryText.text = "$$deliveryFee"
        binding.priceText.text = "$$total"
    }
}