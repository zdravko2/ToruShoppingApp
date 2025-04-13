package com.example.torushoppingapp.Domain

import com.google.firebase.database.PropertyName
import java.io.Serializable

data class FavoriteModel(
    var id: String = "",
    @get:PropertyName("items")
    @set:PropertyName("items")
    var items: List<FavoriteItem> = listOf(),
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId: String = ""
) : Serializable

data class FavoriteItem(
    @get:PropertyName("product_id")
    @set:PropertyName("product_id")
    var productId: String = ""
) : Serializable
