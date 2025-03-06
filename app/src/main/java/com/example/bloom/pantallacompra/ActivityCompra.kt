package com.example.bloom.pantallacompra

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bloom.R
import com.example.bloom.compra.ComprasFragment
import com.example.bloom.navigation.FragmentBottom
import com.example.bloom.navigation.Fragment_Header
import com.example.bloom.pantallahome.HomeFragment

class ActivityCompra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)

        // Cargar Fragment_Header y FragmentBottom (estos siempre son los mismos)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom, FragmentBottom())
            .commit()

        // Leer el fragmento a mostrar
        val fragmentName = intent.getStringExtra("fragment_a_mostrar")

        // Mostrar el Fragment adecuado
        val fragment = when (fragmentName) {
            "ComprasFragment" -> ComprasFragment()
            else -> HomeFragment() // Si no hay nada, cargar Home por defecto
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .commit()
    }
}
