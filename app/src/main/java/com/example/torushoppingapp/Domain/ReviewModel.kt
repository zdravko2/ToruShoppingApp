package com.example.torushoppingapp.Domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class ReviewModel(
    var id:String = "",
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId:String = "",
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var productId:String = "",
    var comment:String = "",
    var rating:Double = 0.0
) : Serializable
