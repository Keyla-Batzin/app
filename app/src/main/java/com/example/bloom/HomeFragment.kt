package com.example.bloom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

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
            // Reemplazar el fragmento dentro del contenedor `R.id.main_fragment`
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, RamosFloresFragment()) // Cambiar a FragmentProductos01
                ?.addToBackStack(null) // Agregar a la pila para retroceso
                ?.commit()
        }

        // Configurar botón para navegar a CategoriasFragment
        val btnCat = view.findViewById<Button>(R.id.btn_cat)
        btnCat.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, CategoriasFragment()) // Cambiar a CategoriasFragment
                ?.addToBackStack(null) // Agregar a la pila para retroceso
                ?.commit()
        }

        // Configurar botón para mostrar un Toast
        val btnSuscribirse = view.findViewById<Button>(R.id.btn_suscribirse)
        btnSuscribirse.setOnClickListener {
            Toast.makeText(requireContext(), "Suscripción realizada con éxito.", Toast.LENGTH_SHORT).show()
        }
    }
}
