package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()
//        val fragment = findViewById<View>(R.id.fragment_container)
//        val textSplash = findViewById<TextView>(R.id.splashText)
//        val background = findViewById<View>(R.id.mainActivityBack1)
//
//        textSplash.alpha=0f
//        textSplash.animate().setDuration(1500).alpha(1f).withEndAction {
//            fragment.visibility=View.VISIBLE
//            background.setBackgroundColor(
//                ContextCompat.getColor(applicationContext,
//                    R.color.white))
//            fragment.visibility=View.VISIBLE
//            textSplash.visibility=View.GONE
//            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
//
//        }
    }
}