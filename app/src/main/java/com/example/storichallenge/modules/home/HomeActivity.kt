package com.example.storichallenge.modules.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.storichallenge.R
import com.example.storichallenge.databinding.HomeActivityBinding
import com.example.storichallenge.extensions.setWindowsSecurityFlags
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        setupNavigation()
        this.setWindowsSecurityFlags()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_home_view) as NavHostFragment
        val navController = navHostFragment.navController
        val graphInflater = navController.navInflater
        navController.graph = graphInflater.inflate(R.navigation.home_nav)
    }
}