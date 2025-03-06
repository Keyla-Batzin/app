package com.example.bloom.categorias

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bloom.R
import com.example.bloom.navigation.FragmentBottom
import com.example.bloom.navigation.Fragment_Header
import com.example.bloom.ramosflores.RamosFloresFragment

class ActivityCategorias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        // Cargar Fragment_Header, HomeFragment y FragmentBottom
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_header, Fragment_Header())
            .commit()

        // En base a lo recibido se muestra un Fragemnt...
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, CategoriasFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_bottom, FragmentBottom())
            .commit()

    }
}