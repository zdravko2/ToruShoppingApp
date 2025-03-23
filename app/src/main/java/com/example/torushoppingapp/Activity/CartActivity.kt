package com.example.torushoppingapp.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.CartAdapter
import com.example.torushoppingapp.Helper.ChangeNumberItemsListener
import com.example.torushoppingapp.Helper.ManagementCart
import com.example.torushoppingapp.R
import com.example.torushoppingapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var managementCart:ManagementCart
    private var tax:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        setVariable()
        calculateCart()
        initCartList()
    }

    private fun calculateCart()
    {
        val percentTax:Double = 0.02
        val delivery:Double = 10.0
        tax = Math.round(managementCart.getTotalFee() * percentTax * 100.0) / 100.0

        val total = Math.round(managementCart.getTotalFee() + tax + delivery * 100.0) / 100.0
        val productTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0

        binding.apply {
            totalFeeText.text = "$" + productTotal.toString()
            totalTaxText.text = "$" + tax.toString()
            deliveryText.text = "$" + delivery.toString()
            priceText.text = "$" + total.toString()
        }
    }

    private fun setVariable()
    {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initCartList() {
        binding.apply {
            listView.layoutManager = LinearLayoutManager(this@CartActivity,
                LinearLayoutManager.VERTICAL, false)
            listView.adapter = CartAdapter(managementCart.getListCart())
            object:ChangeNumberItemsListener{
                override fun onChanged() {
                    calculateCart()
                }
            }
        }
    }
}