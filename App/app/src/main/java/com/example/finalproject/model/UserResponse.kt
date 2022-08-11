package com.example.finalproject.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("token")
    val token: String
)