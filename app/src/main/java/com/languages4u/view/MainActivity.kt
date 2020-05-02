package com.languages4u.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.languages4u.R

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    lateinit var toolbar : Toolbar
    lateinit var drawerlayout : DrawerLayout
    lateinit var navController : NavController
    lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavi()
    }

    fun setupNavi() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        drawerlayout = findViewById(R.id.drawer_layout)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigationView = findViewById(R.id.navi_view)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerlayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        //navigationView.setNavigationItemSelectedListener { this }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment)
            , drawerlayout

        )
    }
}
