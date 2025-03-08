package com.example.bloom.pantallacompra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.bloom.R
import com.example.bloom.compra.ComprasFragment
import com.example.bloom.navigation.FragmentBottom
import com.example.bloom.navigation.Fragment_Header

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

        // Limpiar la pila de retroceso
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        // Reemplazar el fragmento actual con ComprasFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, ComprasFragment(), "TAG_COMPRAS_FRAGMENT")
            .commit()
    }
}
