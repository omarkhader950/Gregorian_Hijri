package com.gregorian_hijri.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gregorian_hijri.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottom = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

         val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment


        bottom.setupWithNavController(NavHostFragment.findNavController(navHostFragment))

       // val navController = navHostFragment.navController




       // bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}