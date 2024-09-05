package com.compfest.chatbothukum.core.domain.pref

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class UserPreference(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
    private val TAG = "UserPreference"

    fun isLoggedIn(): Boolean {
        val loggedIn = preferences.getBoolean("logged_in", false)
        Log.d(TAG, "isLoggedIn: $loggedIn")
        return loggedIn
    }

    fun setLoggedIn(loggedIn: Boolean) {
        preferences.edit().putBoolean("logged_in", loggedIn).apply()
        Log.d(TAG, "setLoggedIn: $loggedIn")
    }

    fun setUserEmail(email: String) {
        preferences.edit().putString("user_email", email).apply()
        Log.d(TAG, "setUserEmail: $email")
    }

    fun getUserEmail(): String? {
        val email = preferences.getString("user_email", null)
        Log.d(TAG, "getUserEmail: $email")
        return email
    }

    fun setUid(uid: String) {
        Log.d("UserPreference", "Saving UID: $uid")
        preferences.edit().putString("uid", uid).apply()
    }

    fun getUid(): String? {
        val uid = preferences.getString("uid", null)
        Log.d("UserPreference", "Retrieved UID: $uid")
        return uid
    }

    fun clear() {
        preferences.edit().clear().apply()
        Log.d(TAG, "clear: SharedPreferences cleared")
    }
}
