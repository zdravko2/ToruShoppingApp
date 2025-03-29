package com.example.torushoppingapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.torushoppingapp.R
import com.example.torushoppingapp.databinding.ActivityReviewListBinding

class ReviewListActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark)

        initButtons()
    }

    private fun initButtons() {
        //binding.backButton.setOnClickListener {
            finish()
        //}
    }
}