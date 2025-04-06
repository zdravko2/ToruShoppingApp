package com.example.torushoppingapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.torushoppingapp.Domain.BannerModel
import com.example.torushoppingapp.Domain.CartItem
import com.example.torushoppingapp.Domain.CartModel
import com.example.torushoppingapp.Domain.CategoryModel
import com.example.torushoppingapp.Domain.OrderItem
import com.example.torushoppingapp.Domain.OrderModel
import com.example.torushoppingapp.Domain.ProductModel
import com.example.torushoppingapp.Domain.ReviewModel
import com.example.torushoppingapp.Domain.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Query
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("banners")
        ref.addValueEventListener(object : ValueEventListener {
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

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("categories")
        ref.addValueEventListener(object : ValueEventListener {
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

    fun loadPopular(): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("products")
        ref.addValueEventListener(object : ValueEventListener {
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

    fun loadProductCategory(categoryId: String): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("products")

        val query: Query = ref.orderByChild("category_id").equalTo(categoryId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun loadUser(userId: String): LiveData<MutableList<UserModel>> {
        val listData = MutableLiveData<MutableList<UserModel>>()
        val ref = firebaseDatabase.getReference("users")

        val query: Query = ref.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun registerUser(user: UserModel): LiveData<UserModel?> {
        val result = MutableLiveData<UserModel?>()
        val ref = firebaseDatabase.getReference("users")

        // Check if the email already exists
        val query = ref.orderByChild("email").equalTo(user.email)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Email already in use
                    result.value = null
                } else {
                    // Create a new user node
                    val newUserRef = ref.push()
                    val newUserId = newUserRef.key ?: return
                    val userToSave = user.copy(id = newUserId)

                    newUserRef.setValue(userToSave).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            result.value = userToSave
                        } else {
                            result.value = null
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return result
    }

    fun loadProduct(productId: String): LiveData<MutableList<ProductModel>> {
        val listData = MutableLiveData<MutableList<ProductModel>>()
        val ref = firebaseDatabase.getReference("products")

        val query: Query = ref.orderByChild("user_id").equalTo(productId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ProductModel>()
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(ProductModel::class.java)
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

    fun loadReview(productId: String): LiveData<MutableList<ReviewModel>> {
        val listData = MutableLiveData<MutableList<ReviewModel>>()
        val ref = firebaseDatabase.getReference("reviews")

        val query: Query = ref.orderByChild("product_id").equalTo(productId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun loadCart(userId: String): LiveData<MutableList<CartModel>> {
        val cartData = MutableLiveData<MutableList<CartModel>>()
        val ref = firebaseDatabase.getReference("users").child(userId).child("cart")

        val query: Query = ref.orderByChild("user_id").equalTo(userId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartList = mutableListOf<CartModel>()

                for (cartSnapshot in snapshot.children) {
                    val cart = cartSnapshot.getValue(CartModel::class.java)
                    if (cart != null) {
                        cartList.add(cart)
                    }
                }
                cartData.value = cartList
            }

            override fun onCancelled(error: DatabaseError) {
                cartData.value = mutableListOf()
            }
        })

        return cartData
    }

    fun loadProductsFromCart(userId: String): LiveData<MutableList<Pair<ProductModel, CartItem>>> {
        val liveData = MutableLiveData<MutableList<Pair<ProductModel, CartItem>>>()
        val cartRef = firebaseDatabase.getReference("cart")
        val productsRef = firebaseDatabase.getReference("products")

        // Query the "cart" node for entries where "user_id" equals the given userId
        val query = cartRef.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Assuming one cart per user
                var cartItems: List<CartItem> = listOf()
                for (cartSnapshot in snapshot.children) {
                    val cartModel = cartSnapshot.getValue(CartModel::class.java)
                    if (cartModel != null) {
                        cartItems = cartModel.items
                        break  // We take the first matching cart
                    }
                }
                if (cartItems.isEmpty()) {
                    liveData.value = mutableListOf()
                    return
                }

                val productList = mutableListOf<Pair<ProductModel, CartItem>>()
                var remainingItems = cartItems.size

                // For each cart item, fetch the product details
                for (cartItem in cartItems) {
                    productsRef.child(cartItem.productId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(productSnapshot: DataSnapshot) {
                                val product = productSnapshot.getValue(ProductModel::class.java)
                                product?.let {
                                    productList.add(Pair(it, cartItem))
                                }
                                remainingItems--
                                if (remainingItems == 0) {
                                    liveData.value = productList
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                remainingItems--
                                if (remainingItems == 0) {
                                    liveData.value = productList
                                }
                            }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.value = mutableListOf()
            }
        })

        return liveData
    }

    fun addProductToCart(userId: String, productId: String, quantity: Int?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val cartRootRef = firebaseDatabase.getReference("cart")

        // Step 1: Search for existing cart by user_id
        val query = cartRootRef.orderByChild("user_id").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var cartKey: String? = null
                var cart: CartModel? = null

                for (child in snapshot.children) {
                    val model = child.getValue(CartModel::class.java)
                    val dbUserId = child.child("user_id").getValue(String::class.java)
                    if (model != null && dbUserId == userId) {
                        cartKey = child.key
                        cart = model
                        break
                    }
                }

                // Step 2: If cart exists, update it
                val items = cart?.items?.toMutableList() ?: mutableListOf()
                val existingItem = items.find { it.productId == productId }

                if (existingItem != null) {
                    existingItem.quantity += quantity ?: 1
                } else {
                    items.add(CartItem(productId = productId, quantity = quantity ?: 1))
                }

                val updatedCart: CartModel
                val finalCartRef: DatabaseReference

                if (cartKey != null && cart != null) {
                    // Use existing cart
                    updatedCart = CartModel(
                        id = cart.id,
                        userId = cart.userId,
                        items = items
                    )
                    finalCartRef = cartRootRef.child(cartKey)
                } else {
                    // Create new cart with unique ID
                    val newCartRef = cartRootRef.push()
                    val newCartId = newCartRef.key ?: return
                    updatedCart = CartModel(
                        id = newCartId,
                        userId = userId,
                        items = items
                    )
                    finalCartRef = newCartRef
                }

                // Step 3: Save cart
                finalCartRef.setValue(updatedCart).addOnCompleteListener { task ->
                    result.value = task.isSuccessful
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result.value = false
            }
        })

        return result
    }


    fun removeProductFromCart(userId: String, productId: String, quantity: Int?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val cartRef = firebaseDatabase.getReference("cart")

        val query = cartRef.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var cartKey: String? = null
                var cart: CartModel? = null

                for (child in snapshot.children) {
                    val model = child.getValue(CartModel::class.java)
                    val dbUserId = child.child("user_id").getValue(String::class.java)

                    if (model != null && dbUserId == userId) {
                        cartKey = child.key
                        cart = model
                        break
                    }
                }

                if (cartKey != null && cart != null) {
                    val updatedItems = cart.items.filter { it.productId != productId }

                    val updatedCart = CartModel(
                        id = cart.id,
                        userId = cart.userId,
                        items = updatedItems
                    )

                    cartRef.child(cartKey).setValue(updatedCart).addOnCompleteListener { task ->
                        result.value = task.isSuccessful
                    }
                } else {
                    result.value = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result.value = false
            }
        })

        return result
    }


    fun loadOrders(userId: String): LiveData<MutableList<OrderModel>> {
        val listData = MutableLiveData<MutableList<OrderModel>>()
        val ref = firebaseDatabase.getReference("orders")

        val query: Query = ref.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun loadProductsFromOrder(orderId: String): LiveData<MutableList<Pair<ProductModel, OrderItem>>> {
        val liveData = MutableLiveData<MutableList<Pair<ProductModel, OrderItem>>>()
        val orderRef = firebaseDatabase.getReference("orders").child(orderId).child("items")
        val productsRef = firebaseDatabase.getReference("products")

        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderItems = mutableListOf<OrderItem>()
                for (childSnapshot in snapshot.children) {
                    val orderItem = childSnapshot.getValue(OrderItem::class.java)
                    orderItem?.let { orderItems.add(it) }
                }

                if (orderItems.isEmpty()) {
                    liveData.value = mutableListOf() // Return empty list if no order items
                    return
                }

                val productList = mutableListOf<Pair<ProductModel, OrderItem>>()
                var remainingItems = orderItems.size

                // For each order item, fetch the product details
                for (orderItem in orderItems) {
                    val productId = orderItem.productId
                    productsRef.child(productId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(productSnapshot: DataSnapshot) {
                            val product = productSnapshot.getValue(ProductModel::class.java)
                            if (product != null) {
                                productList.add(Pair(product, orderItem))
                            }
                            remainingItems--
                            if (remainingItems == 0) {
                                liveData.value = productList
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            remainingItems--
                            if (remainingItems == 0) {
                                liveData.value = productList
                            }
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                liveData.value = mutableListOf() // Return empty list if loading fails
            }
        })

        return liveData
    }

    fun placeOrder(userId: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val db = firebaseDatabase

        val cartRef = db.getReference("cart")
        val productRef = db.getReference("products")
        val orderRef = db.getReference("orders")

        // 1. Find user's cart
        val query = cartRef.orderByChild("user_id").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(cartSnapshot: DataSnapshot) {
                var cartKey: String? = null
                var cart: CartModel? = null

                for (child in cartSnapshot.children) {
                    val model = child.getValue(CartModel::class.java)
                    val dbUserId = child.child("user_id").getValue(String::class.java)
                    if (model != null && dbUserId == userId) {
                        cartKey = child.key
                        cart = model
                        break
                    }
                }

                if (cart == null || cart.items.isEmpty()) {
                    result.value = false
                    return
                }

                val cartItems = cart.items
                val orderItems = mutableListOf<OrderItem>()
                var totalPrice = 0.0
                var failed = false
                var remaining = cartItems.size

                for (item in cartItems) {
                    productRef.child(item.productId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(productSnapshot: DataSnapshot) {
                            val product = productSnapshot.getValue(ProductModel::class.java)

                            if (product == null || product.stock < item.quantity) {
                                failed = true
                            } else {
                                totalPrice += product.price * item.quantity
                                orderItems.add(
                                    OrderItem(
                                        productId = item.productId,
                                        quantity = item.quantity,
                                        price = product.price
                                    )
                                )
                            }

                            remaining--
                            if (remaining == 0) {
                                if (failed) {
                                    result.value = false
                                } else {
                                    val newOrderRef = orderRef.push()
                                    val orderId = newOrderRef.key ?: return
                                    val newOrder = OrderModel(
                                        id = orderId,
                                        userId = userId,
                                        orderItems = orderItems,
                                        totalPrice = totalPrice,
                                        status = "Processing"
                                    )

                                    newOrderRef.setValue(newOrder).addOnCompleteListener { orderTask ->
                                        if (orderTask.isSuccessful && cartKey != null) {
                                            // Update stock
                                            for (ci in cartItems) {
                                                val stockRef = productRef.child(ci.productId).child("stock")
                                                stockRef.runTransaction(object : Transaction.Handler {
                                                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                                                        val currentStock = currentData.getValue(Int::class.java) ?: 0
                                                        currentData.value = currentStock - ci.quantity
                                                        return Transaction.success(currentData)
                                                    }

                                                    override fun onComplete(error: DatabaseError?, committed: Boolean, snapshot: DataSnapshot?) {
                                                        // No-op
                                                    }
                                                })
                                            }

                                            // Clear cart
                                            cartRef.child(cartKey).removeValue()
                                            result.value = true
                                        } else {
                                            result.value = false
                                        }
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            failed = true
                            remaining--
                            if (remaining == 0) result.value = false
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                result.value = false
            }
        })

        return result
    }

}