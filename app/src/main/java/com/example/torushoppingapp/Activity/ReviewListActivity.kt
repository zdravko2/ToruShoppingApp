package com.example.torushoppingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.torushoppingapp.Adapter.ReviewListAdapter
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityReviewListBinding

class ReviewListActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewListBinding
    private var viewModel = MainViewModel()
    private var productId = ""
    private var productTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()

        getBundle()
        initButtons()
        initList()
    }

    fun initButtons()
    {
        binding.apply {
            backButton.setOnClickListener {
                finish()
            }

            postButton.setOnClickListener {
                val rating = ratingBar.rating.toDouble()
                val comment = editTextText.text.toString().trim()
                val userId = SessionManager.getUserId(this@ReviewListActivity) ?: return@setOnClickListener
                val productId = productId

                if (rating == 0.0 || comment.isEmpty()) {
                    Toast.makeText(this@ReviewListActivity, "Please provide a rating and comment", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.postReview(productId, userId, rating, comment).observe(this@ReviewListActivity) { updatedReviews ->
                    Toast.makeText(this@ReviewListActivity, "Review posted!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@ReviewListActivity, ReviewListActivity::class.java)
                    intent.putExtra("id", productId)
                    intent.putExtra("title", productTitle)
                    startActivity(intent)

                    finish()
                }
            }
        }
    }

    private fun getBundle() {
        productId = intent.getStringExtra("id")!!
        productTitle = intent.getStringExtra("title")!!

        binding.apply {
            productTitleText.text = productTitle
        }
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            emptyReviewListText.visibility = View.GONE
            viewModel.loadReviews(productId).observe(this@ReviewListActivity, Observer { reviews ->
                if (reviews.isEmpty()) {
                    emptyReviewListText.visibility = View.VISIBLE
                    listView.visibility = View.GONE
                } else {
                    listView.visibility = View.VISIBLE
                    listView.layoutManager = LinearLayoutManager(this@ReviewListActivity,
                        LinearLayoutManager.VERTICAL, false)
                    listView.adapter = ReviewListAdapter(reviews)
                }
                progressBar.visibility = View.GONE
            })
        }
    }
}