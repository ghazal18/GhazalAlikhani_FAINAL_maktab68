package com.example.myapplication


import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.ui.MainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        val buttonProfile = findViewById<ImageButton>(R.id.buttonProfile)
        val buttonShop = findViewById<ImageButton>(R.id.buttonshop)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        buttonProfile.setOnClickListener {
            navController.navigate(R.id.signinFragment)
        }

        buttonShop.setOnClickListener {
            navController.navigate(R.id.orderFragment)
        }

    }


}



