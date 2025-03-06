package com.example.bloom.pantallahome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bloom.ActivityProductos
import com.example.bloom.R
import com.example.bloom.categorias.ActivityCategorias
import com.example.bloom.categorias.CategoriasFragment
import com.example.bloom.pantallacompra.ActivityCompra
import com.example.bloom.ramosflores.RamosFloresFragment
import kotlinx.coroutines.cancel

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para este fragmento
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar CardView para navegar a FragmentProductos01
        val cardRamo = view.findViewById<CardView>(R.id.cardRamo)
        cardRamo.setOnClickListener {
            val intent = Intent(requireContext(), ActivityProductos::class.java)
            startActivity(intent)
        }

        // Configurar botón para navegar a CategoriasFragment
        val btnCat = view.findViewById<Button>(R.id.btn_cat)
        btnCat.setOnClickListener {
            Log.d("HomeFragment", "Botón de categorías clickeado")
            val intent = Intent(requireContext(), ActivityCategorias::class.java)
            startActivity(intent)
        }

        // Configurar botón para mostrar un Toast
        val btnSuscribirse = view.findViewById<Button>(R.id.btn_suscribirse)
        btnSuscribirse.setOnClickListener {
            Toast.makeText(requireContext(), "Suscripción realizada con éxito.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cancela las corrutinas si es necesario
        viewLifecycleOwner.lifecycleScope.cancel()
    }
}
