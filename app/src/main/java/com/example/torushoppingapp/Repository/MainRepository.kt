package com.example.torushoppingapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.example.torushoppingapp.Domain.BannerModel
import com.example.torushoppingapp.Domain.CategoryModel
import com.example.torushoppingapp.Domain.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner():LiveData<MutableList<BannerModel>>{
        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("banners")
        ref.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(BannerModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listData
    }

    fun loadCategory():LiveData<MutableList<CategoryModel>>{
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("categories")
        ref.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listData
    }

    fun loadPopular():LiveData<MutableList<ProductModel>>{
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("products")
        ref.addValueEventListener(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ProductModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return listData
    }

    fun loadProductCategory(categoryId:String) : LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("products")

        val query:Query = ref.orderByChild("category_id").equalTo(categoryId)
        query.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ProductModel::class.java)
                    item?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return listData
    }
}