package com.example.torushoppingapp.Domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class CartModel(
    var id: String = "",
    @get:PropertyName("items")
    @set:PropertyName("items")
    var items: List<CartItem> = listOf(),
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String = ""
) : Serializable

data class CartItem(
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var productId: String = "",
    @get:PropertyName("quantity")
    @set:PropertyName("quantity")
    var quantity: Int = 0
) : Serializable