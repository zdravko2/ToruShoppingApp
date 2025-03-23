package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Activity.ProductDetailActivity
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.databinding.ViewholderPopularBinding

class PopularAdapter(val products:MutableList<ProductModel>)
    : RecyclerView.Adapter<PopularAdapter.Viewholder>() {
        lateinit var context: Context
    class Viewholder(val binding:ViewholderPopularBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.Viewholder {
        context = parent.context
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.Viewholder, position: Int) {
        holder.binding.titleText.text = products[position].title
        holder.binding.priceText.text = "$" + products[position].price.toString()

        Glide.with(context)
            .load(products[position].picURL)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("object", products[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = products.size
}