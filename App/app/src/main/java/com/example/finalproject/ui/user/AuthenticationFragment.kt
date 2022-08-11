package com.example.finalproject.ui.user

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentAuthenticationBinding
import com.example.finalproject.model.AuthRequest
import com.example.finalproject.model.UserRequest
import com.example.finalproject.utils.BaseResponse
import com.example.finalproject.utils.SessionManager

class AuthenticationFragment : DialogFragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private lateinit var viewModel: AuthenticationViewModel
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        val token = SessionManager(requireActivity()).userToken

        if (!token.isNullOrEmpty()) {
            navigateToHome()
        }

        viewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        binding.logIn.setOnClickListener {
            binding.singUp.background = null
            binding.singUp.setTextColor(resources.getColor(R.color.pinkColor, null))
            binding.logIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            binding.singUpLayout.visibility = View.GONE
            binding.logInLayout.visibility = View.VISIBLE
            binding.logIn.setTextColor(resources.getColor(R.color.textColor, null))
        }

        binding.singUp.setOnClickListener {
            binding.singUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            binding.singUp.setTextColor(resources.getColor(R.color.textColor, null))
            binding.logIn.background = null
            binding.singUpLayout.visibility = View.VISIBLE
            binding.logInLayout.visibility = View.GONE
            binding.logIn.setTextColor(resources.getColor(R.color.pinkColor, null))
        }

        binding.registerBtn.setOnClickListener {
            createUser()
        }

        binding.singIn.setOnClickListener {
            doLogIn()
        }

        return binding.root

    }

    private fun doLogIn() {
        val sUser = AuthRequest(binding.eMail.text.toString(), binding.password.text.toString())
        viewModel.userLogin(sUser)
        viewModel.singIn.observe(viewLifecycleOwner, {
            when (it) {
                is BaseResponse.Loading -> {}
                is BaseResponse.Success -> {
                    findNavController().navigate(R.id.addingComplexFragment)
                }
                is BaseResponse.Error -> it.msg?.let { msg -> requireActivity() }
            }
        })
    }

    private fun createUser() {
        val rUser = UserRequest(
            binding.etLogIn.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
        viewModel.register(rUser)
        viewModel.userLD.observe(viewLifecycleOwner, {
            when (it) {
                is BaseResponse.Loading -> {}
                is BaseResponse.Success -> {
                    Toast.makeText(requireActivity(), "Registration Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeFragment)
                }
                is BaseResponse.Error -> it.msg?.let { msg -> requireActivity() }
            }
        })
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.addingComplexFragment)
    }
}