package com.example.bloom

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bloom.compra.ComprasFragment
import com.example.bloom.navigation.FragmentBottom
import com.example.bloom.navigation.Fragment_Header
import com.example.bloom.ramosflores.RamosFloresFragment

class ActivityProductos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        // Gestonar el bundle recibido


        // Cargar Fragment_Header, HomeFragment y FragmentBottom
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        // En base a lo recibido se muestra un Fragemnt...
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, RamosFloresFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom, FragmentBottom())
            .commit()
    }
}