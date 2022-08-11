package com.example.finalproject.repository
import com.example.finalproject.model.*
import com.example.finalproject.retrofit.ApiClient
import retrofit2.Response

class UserRepository {
    private val api = ApiClient.api

    suspend fun userRegister(userRequest: UserRequest): Response<UserResponse>{
        return api.register(userRequest)
    }

    suspend fun loginUser(authRequest: AuthRequest): Response<AuthResponse> {
        return api.login(authRequest)
    }

}