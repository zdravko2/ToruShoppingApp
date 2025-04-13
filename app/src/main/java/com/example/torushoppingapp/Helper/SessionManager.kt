package com.example.torushoppingapp.Helper

import android.content.Context
import androidx.core.content.edit
import com.example.torushoppingapp.Domain.UserModel

object SessionManager {
    fun getUserId(context: Context): String? {
        val prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        return prefs.getString("userId", null)
    }

    fun getUserName(context: Context): String? {
        val prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        return prefs.getString("userName", null)
    }

    fun getUserEmail(context: Context): String? {
        val prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        return prefs.getString("userEmail", null)
    }

    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun getTheme(context: Context): Int {
        val prefs = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("theme", 0)
    }
    fun setTheme(context: Context, theme: Int) {
        val prefs = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
        prefs.edit {
            putInt("theme", theme)
        }
    }

    fun getNotifications(context: Context): Int {
        val prefs = context.getSharedPreferences("NotificationsPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("notifications", 0)
    }

    fun setNotifications(context: Context, notifications: Int) {
        val prefs = context.getSharedPreferences("NotificationsPrefs", Context.MODE_PRIVATE)
        prefs.edit {
            putInt("notifications", notifications)
        }
    }

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        prefs.edit() { clear() }
    }

    fun saveLoginSession(context: Context, user: UserModel) {
        val sharedPref = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            putString("userId", user.id)
            putString("userName", user.name)
            putString("userEmail", user.email)
            apply()
        }
    }
}
