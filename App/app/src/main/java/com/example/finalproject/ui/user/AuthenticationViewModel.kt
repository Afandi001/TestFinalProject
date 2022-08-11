package com.example.finalproject.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.App
import com.example.finalproject.model.AuthRequest
import com.example.finalproject.model.UserRequest
import com.example.finalproject.model.UserResponse
import com.example.finalproject.repository.UserRepository
import com.example.finalproject.utils.BaseResponse
import com.example.finalproject.utils.SessionManager
import kotlinx.coroutines.launch


class AuthenticationViewModel : ViewModel() {

    private val repository = UserRepository()
    private val pref = SessionManager(App.instance.applicationContext)
    val userLD: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val singIn: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()

    fun userLogin(user: AuthRequest) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(user)
                pref.userToken = response.body()?.userToken
                if (response.body()?.userToken == null) {
                    singIn.value = BaseResponse.Error(response.message())
                } else {
                    singIn.value = BaseResponse.Success()
                }
            } catch (ex: Exception) {
                singIn.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun register(user: UserRequest) {
        viewModelScope.launch {
            try {
                val response = repository.userRegister(user)
                if (response.code() == 200) {
                    userLD.value = BaseResponse.Success(response.body())
                    pref.userToken = response.body()?.token
                    Log.e("TOKEN", pref.userToken!!)
                } else {
                    userLD.value = BaseResponse.Error(response.message())
                }
            } catch (ex: Exception) {
                userLD.value = BaseResponse.Error(ex.message)
            }
        }
    }
}