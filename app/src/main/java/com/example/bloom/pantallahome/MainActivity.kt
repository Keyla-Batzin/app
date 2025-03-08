package com.example.bloom.pantallahome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bloom.R
import com.example.bloom.databinding.ActivityMainBinding
import com.example.bloom.navigation.FragmentBottom
import com.example.bloom.navigation.Fragment_Header

class MainActivity : AppCompatActivity() {
    /* private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Configurar SplashScreen
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Inicializar View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { false }

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
    */
}
