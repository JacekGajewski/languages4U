package com.languages4u.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.languages4u.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav_bar.setupWithNavController(navController)
        bottomNavBarVisibility()
    }

    private fun bottomNavBarVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
//                R.id.listView -> bottom_nav_bar.visibility = View.VISIBLE
//                R.id.menuView -> bottom_nav_bar.visibility = View.VISIBLE
//                R.id.chooseQuizView -> bottom_nav_bar.visibility = View.VISIBLE
//                R.id.authView -> bottom_nav_bar.visibility = View.GONE
//                R.id.detailsView -> bottom_nav_bar.visibility = View.GONE
            }
        }

    }
}
