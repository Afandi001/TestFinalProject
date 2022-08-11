package com.example.finalproject.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userToken")
    val userToken: String
)