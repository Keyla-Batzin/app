package com.example.bloom.pantallacompra

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bloom.pantallahome.HomeFragment
import com.example.bloom.R
import com.example.bloom.pantallahome.ActivityPrincipal

class FragmentCompra3 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_compra3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encuentra el botón Continuar
        val btnNext = view.findViewById<Button>(R.id.btn_back)
        // Configura el listener para el botón "Volver al inicio"
        btnNext.setOnClickListener {
            // Cambiar de Activity para ir a Home
            val intent = Intent(requireContext(), ActivityPrincipal::class.java)
            startActivity(intent)
        }

    }
}