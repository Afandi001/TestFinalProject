package com.example.finalproject.ui.addComplex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.ComplexRequest
import com.example.finalproject.model.ComplexResponse
import com.example.finalproject.repository.ComplexRepository
import com.example.finalproject.utils.BaseResponse
import kotlinx.coroutines.launch

class ComplexViewModel: ViewModel() {

    private val repository = ComplexRepository()
    val complexLD: MutableLiveData<BaseResponse<ComplexResponse>> = MutableLiveData()

    fun saveComplex(complex: ComplexRequest) {
        viewModelScope.launch {
             val response = repository.complexSave(complex)
            complexLD.value = BaseResponse.Success(response.body())
            complexLD.value = BaseResponse.Error(response.message())
        }
    }
}