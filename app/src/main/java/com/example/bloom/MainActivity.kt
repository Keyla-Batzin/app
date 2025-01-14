package com.example.bloom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargar el Fragment_Header al iniciar la actividad
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, Fragment_Header())
            .commit()
    }
}
