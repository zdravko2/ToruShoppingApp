package com.example.torushoppingapp.Domain

import java.io.Serializable

data class ReviewModel(
    var id:String = "",
    var userId:String = "",
    var productId:String = "",
    var comment:String = "",
    var rating:Int = 0
) : Serializable
