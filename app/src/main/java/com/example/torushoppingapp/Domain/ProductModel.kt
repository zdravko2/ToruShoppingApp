package com.example.torushoppingapp.Domain

import java.io.Serializable

data class ProductModel(
    var id:String = "",
    var categoryId:String = "0",
    var title:String = "",
    var description:String = "",
    var price:Double = 0.0,
    var picURL:String = "",
    var stock:Int = 0
) : Serializable
