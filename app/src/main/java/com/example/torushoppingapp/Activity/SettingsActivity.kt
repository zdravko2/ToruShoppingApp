package com.example.torushoppingapp.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.se.omapi.Session
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.torushoppingapp.Helper.SessionManager
import com.example.torushoppingapp.R
import com.example.torushoppingapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons() {
        binding.apply {
            backButton.setOnClickListener {
                finish()
            }

            themeSwitch.isChecked = SessionManager.getTheme(this@SettingsActivity) == 1
            themeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    SessionManager.setTheme(this@SettingsActivity, 1)
                } else {
                    SessionManager.setTheme(this@SettingsActivity, 0)
                }
                changeTheme()
            }

            notificationSwitch.isChecked = SessionManager.getNotifications(this@SettingsActivity) == 1
            notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    SessionManager.setNotifications(this@SettingsActivity, 1)
                } else {
                    SessionManager.setNotifications(this@SettingsActivity, 0)
                }
            }
        }
    }

    private fun changeTheme()
    {
        var theme = SessionManager.getTheme(this@SettingsActivity)
        if (theme == 0)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}