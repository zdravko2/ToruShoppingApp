package com.example.torushoppingapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.torushoppingapp.databinding.ActivityProfileBinding
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
        initProfile()
    }

    private fun initButtons()
    {
        binding.backButton.setOnClickListener{
            finish()
        }
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

}