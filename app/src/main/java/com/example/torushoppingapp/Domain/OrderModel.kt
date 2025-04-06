package com.example.torushoppingapp.Domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class OrderModel(
    var id: String = "",

    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String = "",

    @get:PropertyName("items")
    @set:PropertyName("items")
    var orderItems: List<OrderItem> = listOf(),

    var status: String = "",

    @get:PropertyName("total_price")
    @set:PropertyName("total_price")
    var totalPrice: Double = 0.0
) : Serializable

data class OrderItem(
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var productId: String = "",
    var quantity: Int = 0,
    var price: Double = 0.0
) : Serializable