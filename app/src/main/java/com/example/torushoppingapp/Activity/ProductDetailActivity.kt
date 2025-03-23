package com.example.torushoppingapp.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Helper.ManagementCart
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    private lateinit var item: ProductModel
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        bundle()
    }

    private fun bundle() {
        binding.apply {
            item = intent.getSerializableExtra("object") as ProductModel

            Glide.with(this@ProductDetailActivity)
                .load(item.picURL)
                .into(binding.picMain)

            titleText.text = item.title
            descriptionText.text = item.description
            priceText.text = "$" + item.price.toString()

            addToCartButton.setOnClickListener {
                item.numberInCart = 1
                managementCart.insertItems(item)
            }

            backButton.setOnClickListener {
                finish()
            }
        }
    }
}