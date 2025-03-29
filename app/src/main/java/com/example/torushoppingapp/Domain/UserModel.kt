package com.example.torushoppingapp.Domain

data class UserModel(
    val id:String = "",
    val name:String = "",
    val email:String = "",
    val password:String = "",
    var cart: CartModel? = null
)
