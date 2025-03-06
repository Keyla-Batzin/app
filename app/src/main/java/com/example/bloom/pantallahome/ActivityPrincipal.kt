package com.example.bloom.pantallahome

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bloom.R
import com.example.bloom.databinding.ActivityMainBinding
import com.example.bloom.navigation.FragmentBottom
import com.example.bloom.navigation.Fragment_Header

class ActivityPrincipal : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Cargar Fragment_Header, HomeFragment y FragmentBottom
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, HomeFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom, FragmentBottom())
            .commit()
    }
}