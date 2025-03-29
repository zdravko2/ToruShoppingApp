package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Activity.CartActivity
import com.example.torushoppingapp.Activity.ProductDetailActivity
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.databinding.ViewholderProductCartBinding

class CartAdapter(private val productCartList: MutableList<Pair<ProductModel, CartItem>>)
    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(val binding: ViewholderProductCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderProductCartBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (product, cartItem) = productCartList[position]

        holder.binding.titleText.text = product.title
        holder.binding.priceText.text = "$${product.price}"
        holder.binding.quantityText.text = "Qty: ${cartItem.quantity}"

        Glide.with(context)
            .load(product.picURL)
            .into(holder.binding.mainPic)

        // Set an onClickListener if needed to open ProductDetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("object", product)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = productCartList.size
}