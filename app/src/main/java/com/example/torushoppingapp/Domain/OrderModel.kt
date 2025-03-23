package com.example.torushoppingapp.Domain

import java.io.Serializable

data class OrderModel(
    val id:String = "",
    val productId:String = "",
    val userId:String = "",
    val quantity:Int = 0,
    val totalPrice:Double = 0.0,
    val status:String = ""
) : Serializable
