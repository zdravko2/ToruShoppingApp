package com.example.torushoppingapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.torushoppingapp.Domain.ReviewModel
import com.example.torushoppingapp.Domain.UserModel
import com.example.torushoppingapp.ViewModel.MainViewModel
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

        val viewModel = MainViewModel()
        var username = "null"
        viewModel.loadUser(review.userId)
            .observe(context as LifecycleOwner) { success ->
                if (success != null) {
                    username = success.name
                    bindCommonData(username, review.rating, review.comment)
                }
            }
    }

    override fun getItemCount(): Int = reviews.size
}