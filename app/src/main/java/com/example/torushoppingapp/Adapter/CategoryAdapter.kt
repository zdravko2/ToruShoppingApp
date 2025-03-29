package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.torushoppingapp.Activity.ProductListActivity
import com.example.torushoppingapp.Domain.CategoryModel
import com.example.torushoppingapp.R
import com.example.torushoppingapp.databinding.ViewholderCategoryBinding

class CategoryAdapter(val categories:MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {
        private lateinit var context: Context
        private var selectedPosition = -1
        private var lastSelectedPosition = -1

    inner class Viewholder(val binding:ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return  Viewholder((binding))
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val category = categories[position]

        fun bindCommonData(
            categoryId: String,
            titleText: String
        )
        {
            holder.binding.titleCat.text = titleText

            holder.binding.root.setOnClickListener {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(context, ProductListActivity::class.java).apply {
                        putExtra("id", categoryId)
                        putExtra("title", titleText)
                    }
                    ContextCompat.startActivity(context, intent, null)
                }, 100)
            }

            if (selectedPosition == position) {
                holder.binding.titleCat.setBackgroundResource(R.drawable.dark_blue_bg)
                holder.binding.titleCat.setTextColor(context.resources.getColor(R.color.white))
            } else {
                holder.binding.titleCat.setBackgroundResource(R.drawable.white_bg)
                holder.binding.titleCat.setTextColor(context.resources.getColor(R.color.dark_blue))
            }
        }

        bindCommonData(category.id, category.name)
    }

    override fun getItemCount(): Int = categories.size
}