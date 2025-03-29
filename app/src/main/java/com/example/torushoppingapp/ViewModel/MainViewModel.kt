package com.example.torushoppingapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.torushoppingapp.Domain.BannerModel
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.CartModel
import com.example.torushoppingapp.Domain.CategoryModel
import com.example.torushoppingapp.Domain.OrderItem
import com.example.torushoppingapp.Domain.OrderModel
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Domain.ReviewModel
import com.example.torushoppingapp.Domain.UserModel
import com.example.torushoppingapp.Repository.MainRepository

class MainViewModel: ViewModel() {
    private val repository = MainRepository()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadPopular(): LiveData<MutableList<ProductModel>> {
        return repository.loadPopular()
    }

    fun loadProductCategory(categoryId:String) : LiveData<MutableList<ProductModel>> {
        return repository.loadProductCategory(categoryId)
    }

    fun loadProduct(productId:String): LiveData<MutableList<ProductModel>> {
        return repository.loadProduct(productId)
    }

    fun loadUser(userId:String): LiveData<MutableList<UserModel>> {
        return repository.loadUser(userId)
    }

    fun loadReview(productId:String) : LiveData<MutableList<ReviewModel>> {
        return repository.loadReview(productId)
    }

    fun loadCart(userId:String) : LiveData<MutableList<CartModel>> {
        return repository.loadCart(userId)
    }

    fun loadProductsFromCart(userId:String) : LiveData<MutableList<Pair<ProductModel, CartItem>>> {
        return repository.loadProductsFromCart(userId)
    }

    fun loadOrder(userId:String) : LiveData<MutableList<OrderModel>> {
        return repository.loadOrder(userId)
    }

    fun loadProductsFromOrder(orderId:String) : LiveData<MutableList<Pair<ProductModel, OrderItem>>> {
        return repository.loadProductsFromOrder(orderId)
    }
}