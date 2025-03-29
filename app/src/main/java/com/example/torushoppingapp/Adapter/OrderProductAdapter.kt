package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Activity.ProductDetailActivity
import com.example.torushoppingapp.Domain.OrderItem
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.databinding.ViewholderOrderProductBinding

class OrderProductAdapter(private val productOrderList: MutableList<Pair<ProductModel, OrderItem>>) :
    RecyclerView.Adapter<OrderProductAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(val binding: ViewholderOrderProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderOrderProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (product, orderItem) = productOrderList[position]

        fun bindCommonData(
            titleText:String,
            priceText:String,
            quantityText:String,
            picURL:String
        ) {
            holder.binding.titleText.text = titleText
            holder.binding.priceText.text = priceText
            holder.binding.quantityText.text = quantityText

            Glide.with(context)
                .load(picURL)
                .into(holder.binding.mainPic)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("object", product)
                context.startActivity(intent)
            }
        }

        bindCommonData(product.title, "$${product.price}", "Quantity: ${orderItem.quantity}", product.picURL)
    }

    override fun getItemCount(): Int = productOrderList.size
}
