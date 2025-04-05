package com.example.torushoppingapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.databinding.ActivityProfileBinding

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
        binding.apply{
            backButton.setOnClickListener{
                finish()
            }

            logOutButton.setOnClickListener{
                SessionManager.logout(this@ProfileActivity)

                val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

    }

    private fun initProfile()
    {
        binding.usernameText.text = SessionManager.getUserId(this)
        binding.emailText.text = SessionManager.getUserEmail(this)
    }
}