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
import com.example.torushoppingapp.Domain.FavoriteItem
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ViewholderFavoriteProductBinding

class FavoriteListAdapter (private val productFavoritesList: MutableList<Pair<ProductModel, FavoriteItem>>)
    : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

        lateinit var context: Context
        lateinit var viewModel: MainViewModel

        class ViewHolder(val binding: ViewholderFavoriteProductBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            context = parent.context
            val binding = ViewholderFavoriteProductBinding.inflate(LayoutInflater.from(context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val (product, favoriteItem) = productFavoritesList[position]

            fun bindCommonData(
                titleText:String,
                priceText:String,
                picURL:String
            ) {
                holder.binding.titleText.text = titleText
                holder.binding.priceText.text = priceText

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
                        val favoriteItem = productFavoritesList[position].second
                        val userId = SessionManager.getUserId(context).toString()


                        viewModel = MainViewModel()
                        viewModel.toggleProductFavorite(userId, favoriteItem.productId)
                            .observe(context as LifecycleOwner) { success ->
                                if (success) {
                                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()

                                    productFavoritesList.removeAt(position)
                                    notifyItemRemoved(position)
                                    notifyItemRangeChanged(position, productFavoritesList.size)
                                } else {
                                    Toast.makeText(context, "Failed to remove from favorites", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }

            }

            bindCommonData(product.title, "$${product.price}", product.picURL)
        }

        override fun getItemCount(): Int = productFavoritesList.size
    }