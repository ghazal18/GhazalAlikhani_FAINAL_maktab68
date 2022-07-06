package com.example.myapplication.ui.activity



import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getLocationPermission()
        val toolbar = findViewById<Toolbar>(R.id.mytoolbar)
        setSupportActionBar(toolbar)
        val buttonProfile = findViewById<ImageButton>(R.id.buttonProfile)
        val buttonSearch = findViewById<ImageButton>(R.id.buttonSearch)
        val buttonDarkMode = findViewById<ImageButton>(R.id.buttonDarkMode)
        val buttonShop = findViewById<ImageButton>(R.id.buttonshop)
        var darkMode = false
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        buttonProfile.setOnClickListener {
            navController.navigate(R.id.signinFragment)
        }
        buttonShop.setOnClickListener {
            navController.navigate(R.id.orderFragment)
        }
        buttonSearch.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }
        buttonDarkMode.setImageResource(R.drawable.ic_baseline_dark_mode_24)
        buttonDarkMode.setOnClickListener {
            if(!darkMode){
                buttonDarkMode.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                buttonDarkMode.setImageResource(R.drawable.ic_baseline_dark_mode_24)
            }
                darkMode = !darkMode
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                } else -> {
            }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }


}



