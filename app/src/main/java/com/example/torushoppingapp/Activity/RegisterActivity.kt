package com.example.torushoppingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.torushoppingapp.Domain.UserModel
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.Repository.MainRepository
import com.example.torushoppingapp.ViewModel.MainViewModel
import com.example.torushoppingapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private var viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons()
    {
        binding.apply {
            registerButton.setOnClickListener {
                val username = binding.usernameText.text.toString().trim()
                val email = binding.emailText.text.toString().trim()
                val password = binding.passwordText.text.toString().trim()

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Please enter username, email and password!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                tryRegister(username, email, password)
            }

            cancelButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun tryRegister(username: String, email: String, password: String)
    {
        val createdUser = UserModel(
            name = username,
            email = email,
            password = password
        )


        val userLiveData = viewModel.registerUser(createdUser)
        userLiveData.observe(this) { user ->
            if (user != null) {
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()

                SessionManager.saveLoginSession(this, user)

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registration failed. Email already in use.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}