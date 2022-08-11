package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ui.user.AuthenticationFragment
import com.example.finalproject.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.main_container)
        navView.setupWithNavController(navController)
    }
    override fun onStart() {
        super.onStart()
        val navController = findNavController(R.id.main_container)
        binding.fab.setOnClickListener {
//            binding.loading.visibility = View.VISIBLE
            if (SessionManager(this).userToken != null)
                navController.navigate(R.id.addingComplexFragment)
            else {
                val dialog = AuthenticationFragment()
                dialog.show(supportFragmentManager, "customDialog")
            }
        }
    }
}