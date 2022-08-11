package com.example.finalproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.finalproject.R

class SessionManager(context: Context) {
    private val APP_PREF_NAME = "App"
    private var preferences: SharedPreferences? = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    var userToken: String?
        get() = preferences?.getString("TOKEN", null)
        set(value) = preferences?.edit()?.putString("TOKEN", value)?.apply()!!
}