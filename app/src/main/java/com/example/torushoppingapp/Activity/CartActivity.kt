package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Activity.ProductListActivity
import com.example.torushoppingapp.Adapter.CartAdapter
import com.example.torushoppingapp.Adapter.ProductListCategoryAdapter
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.CartModel
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel = MainViewModel()
    private var tax: Double = 0.0

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
        viewModel.loadProductsWithCartQuantity(userId).observe(this, Observer { productCartList ->
            if (productCartList.isNullOrEmpty()) {
                //binding.emptyCartText.visibility = View.VISIBLE
                binding.listView.visibility = View.GONE
                binding.totalFeeText.visibility = View.GONE // Hide totals if needed

                Toast.makeText(this, "Cart NOT Loaded", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Cart Loaded", Toast.LENGTH_SHORT).show()
                //binding.emptyCartText.visibility = View.GONE
                binding.listView.visibility = View.VISIBLE
                binding.totalFeeText.visibility = View.VISIBLE

                setupCartList(productCartList)
                calculateCart(productCartList)
            }
        })
    }
    private fun setupCartList(productCartList: MutableList<Pair<ProductModel, CartItem>>) {
        binding.listView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listView.adapter = CartAdapter(productCartList)
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