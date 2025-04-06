package com.example.torushoppingapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.torushoppingapp.Domain.ReviewModel
import com.example.torushoppingapp.databinding.ViewholderReviewBinding

class ReviewListAdapter(val reviews:MutableList<ReviewModel>) : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {
    lateinit var context: Context
    class ViewHolder(val binding: ViewholderReviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderReviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]
        fun bindCommonData(
            username:String,
            rating: Double,
            comment:String
        ){
            holder.binding.usernameText.text = username
            holder.binding.reviewRatingBar.rating = rating.toFloat()
            holder.binding.commentText.text = comment
        }

        bindCommonData(review.userId, review.rating, review.comment)
    }

    override fun getItemCount(): Int = reviews.size
}