package com.example.torushoppingapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.torushoppingapp.Activity.ProductDetailActivity
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ViewholderCartProductBinding

class CartAdapter(private val productCartList: MutableList<Pair<ProductModel, CartItem>>)
    : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    lateinit var context: Context
    lateinit var viewModel: MainViewModel

    class ViewHolder(val binding: ViewholderCartProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderCartProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (product, cartItem) = productCartList[position]

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

            holder.binding.removeButton.setOnClickListener {
                val position = holder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val cartItem = productCartList[position].second
                    val userId = SessionManager.getUserId(context).toString()

                    viewModel = MainViewModel()
                    viewModel.removeProductFromCart(userId, cartItem.productId, 1)
                        .observe(context as LifecycleOwner) { success ->
                            if (success) {
                                Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show()

                                productCartList.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, productCartList.size)
                            } else {
                                Toast.makeText(context, "Failed to remove from cart", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

        }

        bindCommonData(product.title, "$${product.price}", "Quantity: ${cartItem.quantity}", product.picURL)
    }

    override fun getItemCount(): Int = productCartList.size
}