package com.example.bloom.pantallacompra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.bloom.R

class FragmentCarrito : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)

        // Encuentra el botón en el layout inflado
        val button = view.findViewById<Button>(R.id.btn_comprar)

        // Configura el listener para el botón
        button.setOnClickListener {
            // Cambiar a otro fragmento (FragmentCompra1)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCompra1())?.commit()
        }

        return view
    }
}
