package com.example.torushoppingapp.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.torushoppingapp.Domain.UserModel
import com.example.torushoppingapp.R
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityProfileBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.values

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private val viewModel = MainViewModel()
    private var id:String = "0"
    private var username:String = ""
    private var email:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        initProfile()
    }

    private fun initProfile()
    {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        databaseReference.orderByKey().limitToFirst(1).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                for (userSnapshot in snapshot.children) {
                    val username = userSnapshot.child("name").getValue(String::class.java)
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    if (username != null) {
                        binding.usernameText.text = username
                        binding.emailText.text = email
                    } else {
                        binding.usernameText.text = "No username found"
                    }
                }
            } else {
                binding.usernameText.text = "No users found"
            }
        }.addOnFailureListener {
            binding.usernameText.text = "Error retrieving user"
        }
    }

    private fun initButtons()
    {
        binding.backButton.setOnClickListener{
            finish()
        }
    }
}