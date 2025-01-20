package com.example.bloom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSpalsh = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenSpalsh.setKeepOnScreenCondition{false}

        // Cargar Fragment_Header en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, CategoriasFragment())
            .commit()

        // Cargar FragmentBottom en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom, FragmentBottom())
            .commit()
    }
}
