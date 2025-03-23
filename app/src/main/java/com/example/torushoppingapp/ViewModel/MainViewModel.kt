package com.example.torushoppingapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.torushoppingapp.Domain.BannerModel
import com.example.torushoppingapp.Domain.CategoryModel
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

    fun loadUser(userId:String): LiveData<MutableList<UserModel>> {
        return repository.loadUser(userId)
    }

    fun loadReview(productId:String) : LiveData<MutableList<ReviewModel>> {
        return repository.loadReview(productId)
    }

    fun loadOrder(userId:String) : LiveData<MutableList<OrderModel>> {
        return repository.loadOrder(userId)
    }
}