package com.example.torushoppingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.torushoppingapp.Repository.MainRepository
import com.example.torushoppingapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons()
    {
        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val password = binding.passwordText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tryLogin(email, password)
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun tryLogin(email:String, password:String)
    {
        val userLiveData = MainRepository().validateUser(email, password)
        userLiveData.observe(this) { user ->
            if (user != null) {
                Toast.makeText(this, "Welcome, ${user.name}!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}