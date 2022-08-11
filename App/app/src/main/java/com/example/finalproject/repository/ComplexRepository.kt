package com.example.finalproject.repository

import com.example.finalproject.model.ComplexRequest
import com.example.finalproject.model.ComplexResponse
import com.example.finalproject.retrofit.ApiClient.api
import retrofit2.Response

class ComplexRepository {

    suspend fun complexSave(complexResponse: ComplexRequest): Response<ComplexResponse> {
        return api.complex(complexResponse)
    }
}