package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Activity.CartActivity
import com.example.torushoppingapp.Activity.ProductDetailActivity
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.databinding.ViewholderProductCartBinding

class CartAdapter (val products:MutableList<ProductModel>)
    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
        lateinit var context: Context
        class ViewHolder(val binding: ViewholderProductCartBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
            context = parent.context
            val binding = ViewholderProductCartBinding.inflate(LayoutInflater.from(context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
            val product = products[position]
            fun bindCommonData(
                titleText:String,
                priceText:String,
                picURL:String
            ){
                holder.binding.titleText.text = titleText
                holder.binding.priceText.text = priceText

                Glide.with(context)
                    .load(picURL)
                    .into(holder.binding.mainPic)

                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ProductDetailActivity::class.java)
                    intent.putExtra("object", products[position])
                    context.startActivity(intent)
                }
            }

            bindCommonData(product.title, "${product.price}", product.picURL)
        }

        override fun getItemCount(): Int = products.size
    }