package com.example.finalproject.retrofit

import com.example.finalproject.model.*
import com.example.finalproject.utils.Constants

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.USER_REGISTER)
    suspend fun register(
        @Body body: UserRequest
    ): Response<UserResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun login(
        @Body body: AuthRequest
    ): Response<AuthResponse>

    @POST(Constants.COMPLEX_SAVE)
    suspend fun complex(
        @Body body: ComplexRequest
    ): Response<ComplexResponse>

}