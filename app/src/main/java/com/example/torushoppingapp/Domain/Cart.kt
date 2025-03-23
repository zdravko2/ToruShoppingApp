package com.example.torushoppingapp.Domain

data class Cart(
    val id:String = "",
    val productId:String = "",
    val userId:String = "",
    val quantity:Int = 0
)
