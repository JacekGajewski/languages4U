package com.languages4u.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.languages4u.R
import com.languages4u.data.DataOperations
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.OnUserStateChangeListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnUserStateChangeListener, FirebaseAuth.AuthStateListener {
    private val TAG = "MainActivity"

    lateinit var toolbar : Toolbar
    lateinit var drawerlayout : DrawerLayout
    lateinit var navController : NavController
    lateinit var navigationView : NavigationView
    lateinit var headerView : View
    lateinit var headerName : TextView
    lateinit var headerEmail : TextView

    private var mNaviOptionsEnabled = false


    var firebase : DataOperations = FirebaseOperations.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavi()
    }

    fun setupNavi() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        drawerlayout = findViewById(R.id.drawer_layout)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigationView = findViewById(R.id.navi_view)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerlayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        //navigationView.setNavigationItemSelectedListener { this }

        // navi drawer
        navigationView.setNavigationItemSelectedListener(this)
        headerView = navigationView.getHeaderView(0)
        headerEmail = headerView.findViewById(R.id.profile_email)
        headerName = headerView.findViewById(R.id.profile_name)
        firebase.registerListener(this)
        firebase.registerAuthStateListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment)
            , drawerlayout
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onNavigationItemSelected")

        when(item.itemId) {
            R.id.add_quiz -> {
                if(mNaviOptionsEnabled) {
                    navController.navigate(R.id.addQuizView)
                }
            }
            R.id.profile -> {
                if(mNaviOptionsEnabled) {
                    navController.navigate(R.id.userProfileView)
                }
            }
            R.id.sign_out -> {
                if(mNaviOptionsEnabled) {
                    firebase.logout()
                    navController.navigate(R.id.authView)
                }
            }
            R.id.home_view -> {
                if(mNaviOptionsEnabled) {
                    navController.navigate(R.id.startView)
                }
            }
        }
        return true;
    }

    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onUserStateChangeListener(email: String, nick: String) {
        headerEmail.setText(email)
        headerName.setText(nick)
    }

    override fun onAuthStateChanged(auth : FirebaseAuth) {
        if(auth.currentUser != null) {
            headerName.setText(auth.currentUser?.displayName)
            headerEmail.setText(auth.currentUser?.email)
            mNaviOptionsEnabled = true;
        } else {
            headerName.setText("Log in")
            headerEmail.setText("")
            mNaviOptionsEnabled = false;
        }
    }
}
