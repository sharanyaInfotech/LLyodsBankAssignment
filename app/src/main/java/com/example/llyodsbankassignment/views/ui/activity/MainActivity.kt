package com.example.llyodsbankassignment.views.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.llyodsbankassignment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        if(::navController.isInitialized) navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}