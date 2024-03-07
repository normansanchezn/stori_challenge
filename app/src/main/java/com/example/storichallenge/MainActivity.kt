package com.example.storichallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.storichallenge.MainViewModel.StartNavigation
import com.example.storichallenge.MainViewModel.StartNavigation.NavigateLogin
import com.example.storichallenge.MainViewModel.StartNavigation.NavigateUnlock
import com.example.storichallenge.databinding.ActivityMainBinding
import com.example.storichallenge.extensions.setWindowsSecurityFlags
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setWindowsSecurityFlags()
        initViewModel()
        initListeners()
    }

    private fun initListeners() {
        with(viewModel) {
            onNavigateTo().observe(this@MainActivity) {
                handleNavigation(it)
            }
        }
    }

    private fun handleNavigation(navigate: StartNavigation) {
        val controller = findNavController(R.id.fragmentContainerView)
        val graphInflater = controller.navInflater
        val navGraph = graphInflater.inflate(R.navigation.stori_nav)
        val startDestinationId = when(navigate) {
            NavigateUnlock -> R.id.unlockFragment
            NavigateLogin -> R.id.loginFragment
        }
        navGraph.setStartDestination(startDestinationId)
        controller.graph = navGraph
    }

    private fun initViewModel() {
        viewModel.setNavigationDest()
    }
}