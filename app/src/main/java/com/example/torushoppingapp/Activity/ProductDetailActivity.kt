package com.example.torushoppingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: ProductModel
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()

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
                val userId = SessionManager.getUserId(this@ProductDetailActivity) ?: ""
                val quantity = quantityText.text.toString().toIntOrNull() ?: 1 // fallback to 1

                viewModel.addProductToCart(userId, product.id, quantity)
                    .observe(this@ProductDetailActivity) { success ->
                        if (success) {
                            Toast.makeText(this@ProductDetailActivity, "Added to cart", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ProductDetailActivity, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            reviewButton.setOnClickListener {
                val intent = Intent(this@ProductDetailActivity, ReviewListActivity::class.java)
                intent.putExtra("id", product.id)
                intent.putExtra("title", product.title)
                startActivity(intent)
            }

            favoriteButton.setOnClickListener {
                Toast.makeText(this@ProductDetailActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
            }

            minusButton.setOnClickListener {
                val text = quantityText.text.toString()
                val number = text.toIntOrNull() ?: 0
                val updatedNumber = number - 1

                if (updatedNumber > 0)
                {
                    quantityText.text = updatedNumber.toString()
                }
            }

            plusButton.setOnClickListener {
                val text = quantityText.text.toString()
                val number = text.toIntOrNull() ?: 0
                val updatedNumber = number + 1

                if (updatedNumber <= product.stock)
                {
                    quantityText.text = updatedNumber.toString()
                }
                else
                {
                    Toast.makeText(this@ProductDetailActivity, "Only ${product.stock} items available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bundle() {
        binding.apply {
            product = intent. getSerializableExtra("object") as ProductModel

            Glide.with(this@ProductDetailActivity)
                .load(product.picURL)
                .into(binding.picMain)

            titleText.text = product.title
            descriptionText.text = product.description
            priceText.text = "$" + product.price.toString()
        }
    }
}