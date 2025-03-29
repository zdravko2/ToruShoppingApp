package com.example.torushoppingapp.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        bundle()
    }

    private fun initButtons()
    {
        binding.apply {
            backButton.setOnClickListener {
                finish()
            }

            addToCartButton.setOnClickListener {
                Toast.makeText(this@ProductDetailActivity, "Added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bundle() {
        binding.apply {
            product = intent.getSerializableExtra("object") as ProductModel

            Glide.with(this@ProductDetailActivity)
                .load(product.picURL)
                .into(binding.picMain)

            titleText.text = product.title
            descriptionText.text = product.description
            priceText.text = "$" + product.price.toString()
        }
    }
}