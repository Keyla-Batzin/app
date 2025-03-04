package com.example.bloom.pantallacompra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.bloom.R

class FragmentCompra2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_compra2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encuentra el botón Continuar
        val btnNext = view.findViewById<Button>(R.id.btn_next)
        // Configura el listener para el botón "Continuar"
        btnNext.setOnClickListener {
            // Reemplaza el fragmento actual con el fragmento "FragmentCarrito"
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCompra3())?.commit()
        }

        // Encuentra el botón Atras
        val btnBack = view.findViewById<ImageButton>(R.id.btn_back)
        // Configura el listener para el botón "Atras"
        btnBack.setOnClickListener {
            // Reemplaza el fragmento actual con el fragmento "FragmentCompra2"
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCompra1())?.commit()
        }
    }
}