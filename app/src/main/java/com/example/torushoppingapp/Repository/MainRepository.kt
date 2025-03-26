package com.example.torushoppingapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.disklrucache.DiskLruCache.Value
import com.example.torushoppingapp.Domain.BannerModel
import com.example.torushoppingapp.Domain.CategoryModel
import com.example.torushoppingapp.Domain.OrderModel
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Domain.ReviewModel
import com.example.torushoppingapp.Domain.UserModel
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
                    val banner = childSnapshot.getValue(BannerModel::class.java)
                    banner?.let { list.add(it) }
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
                    val category = childSnapshot.getValue(CategoryModel::class.java)
                    category?.let { list.add(it) }
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
                    val product = childSnapshot.getValue(ProductModel::class.java)
                    product?.let { list.add(it) }
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
                    val product = childSnapshot.getValue(ProductModel::class.java)
                    product?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return listData
    }

    fun validateUser(email: String, password: String): LiveData<UserModel?> {
        val userData = MutableLiveData<UserModel?>()
        val ref = firebaseDatabase.getReference("users")

        val query: Query = ref.orderByChild("email").equalTo(email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(UserModel::class.java)
                    if (user != null && user.password == password) {
                        userData.value = user
                        return
                    }
                }
                userData.value = null
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return userData
    }

    fun loadUser(userId:String) : LiveData<MutableList<UserModel>>
    {
        val listData = MutableLiveData<MutableList<UserModel>>()
        val ref = firebaseDatabase.getReference("users")

        val query:Query = ref.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<UserModel>()
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(UserModel::class.java)
                    user?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return listData
    }

    fun loadReview(productId:String): LiveData<MutableList<ReviewModel>> {
        val listData = MutableLiveData<MutableList<ReviewModel>>()
        val ref = firebaseDatabase.getReference("reviews")

        val query:Query = ref.orderByChild("product_id").equalTo(productId)
        query.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ReviewModel>()
                for (childSnapshot in snapshot.children) {
                    val review = childSnapshot.getValue(ReviewModel::class.java)
                    review?.let { list.add(it) }
                }
                listData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return listData
    }

    fun loadOrder(userId: String): LiveData<MutableList<OrderModel>> {
        val listData = MutableLiveData<MutableList<OrderModel>>()
        val ref = firebaseDatabase.getReference("orders")

        val query:Query = ref.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<OrderModel>()
                for (childSnapshot in snapshot.children) {
                    val order = childSnapshot.getValue(OrderModel::class.java)
                    order?.let { list.add(it) }
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