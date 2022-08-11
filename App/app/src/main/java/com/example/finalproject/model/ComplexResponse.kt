package com.example.finalproject.model

data class ComplexResponse (
    val aboutComplex: String,
    val complexName: String,
    val contactInfoResponse: ContactInfoResponse,
    val deleted: Boolean,
    val fileResponses: List<FileResponse>,
    val id: Int,
    val userId: Int
)