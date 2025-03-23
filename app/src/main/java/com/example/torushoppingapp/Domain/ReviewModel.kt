package com.example.torushoppingapp.Domain

import java.io.Serializable

data class ReviewModel(
    val id:String = "",
    val userId:String = "",
    val productId:String = "",
    val comment:String = "",
    val rating:Int = 0
) : Serializable
