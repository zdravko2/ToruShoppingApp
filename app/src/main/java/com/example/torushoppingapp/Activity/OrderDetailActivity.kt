package com.example.torushoppingapp.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Activity.ProductDetailActivity
import com.example.torushoppingapp.Domain.OrderModel
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.R
import com.example.torushoppingapp.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderDetailBinding
    private lateinit var order: OrderModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        bundle()
    }

    fun initButtons()
    {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun bundle() {
        binding.apply {
            order = intent.getSerializableExtra("object") as OrderModel

            //Glide.with(this@OrderDetailActivity)
            //    .load(order.picURL)
            //    .into(binding.picMain)

            orderIdText.text = order.id
            totalPriceText.text = "$" + order.totalPrice.toString()
            statusText.text = order.status
        }
    }
}