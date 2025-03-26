package com.example.torushoppingapp.Domain

data class CartModel(
    val id:String = "",
    val productId:String = "",
    val userId:String = "",
    val quantity:Int = 0
)
