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

    fun searchProducts(query:String): LiveData<MutableList<ProductModel>> {
        return repository.searchProducts(query)
    }

    fun loadUser(userId:String): LiveData<MutableList<UserModel>> {
        return repository.loadUser(userId)
    }

    fun validateUser(email: String, password: String): LiveData<UserModel?> {
        return repository.validateUser(email, password)
    }

    fun registerUser(user: UserModel): LiveData<UserModel?> {
        return repository.registerUser(user)
    }

    fun loadReviews(productId:String) : LiveData<MutableList<ReviewModel>> {
        return repository.loadReviews(productId)
    }

    fun postReview(productId:String, userId: String, rating: Double, comment: String) : LiveData<MutableList<ReviewModel>> {
        return repository.postReview(productId, userId, rating, comment)
    }

    fun loadProductsFromCart(userId:String) : LiveData<MutableList<Pair<ProductModel, CartItem>>> {
        return repository.loadProductsFromCart(userId)
    }

    fun addProductToCart(userId: String, productId: String, quantity: Int?) : LiveData<Boolean>  {
        return repository.addProductToCart(userId, productId, quantity)
    }

    fun removeProductFromCart(userId: String, productId: String, quantity: Int?) : LiveData<Boolean>  {
        return repository.removeProductFromCart(userId, productId, quantity)
    }

    fun loadOrders(userId:String) : LiveData<MutableList<OrderModel>> {
        return repository.loadOrders(userId)
    }

    fun loadProductsFromOrder(orderId:String) : LiveData<MutableList<Pair<ProductModel, OrderItem>>> {
        return repository.loadProductsFromOrder(orderId)
    }

    fun placeOrder(userId: String): LiveData<Boolean> {
        return repository.placeOrder(userId)
    }
}