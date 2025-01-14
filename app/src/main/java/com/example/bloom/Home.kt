package com.example.bloom

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Cargar Fragment_Header en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        // Cargar FragmentBottom en el contenedor correspondiente
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom, FragmentBottom())
            .commit()
    }
}