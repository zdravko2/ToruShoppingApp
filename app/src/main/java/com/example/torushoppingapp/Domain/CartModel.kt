package com.example.torushoppingapp.Domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class CartModel(
    var items: List<CartItem> = listOf() // List of products in the cart
) : Serializable

data class CartItem(
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var productId: String = "",
    var quantity: Int = 0
) : Serializable