package com.example.bloom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bloom.navigation.Fragment_Header
import com.example.bloom.ramosflores.RamosFloresFragment

class PruebaAPi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_api)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        // Cargar Fragment_RamosFlores
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, RamosFloresFragment())
            .commit()
    }
}