package com.example.torushoppingapp.Helper

import android.content.Context
import androidx.core.content.edit

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

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        prefs.edit() { clear() }
    }
}
