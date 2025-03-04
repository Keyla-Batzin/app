package com.example.bloom.pantallacompra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.bloom.R

class FragmentCompra1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_compra1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encuentra el Spinner (desplegable) que muestra la lista de países
        val countrySpinner: Spinner = view.findViewById(R.id.countrySpinner)

        // Crear un adaptador para el Spinner con el array de países
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries_array, // El array de países que está en strings.xml
            android.R.layout.simple_spinner_item
        )

        // Establecer el diseño del desplegable del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al Spinner
        countrySpinner.adapter = adapter

        // Establecer un texto de ayuda que indica al usuario que seleccione un país
        countrySpinner.prompt = getString(R.string.select_country)

        // Encuentra el botón Continuar
        val btnNext = view.findViewById<Button>(R.id.btn_next)
        // Configura el listener para el botón "Continuar"
        btnNext.setOnClickListener {
            // Reemplaza el fragmento actual con el fragmento "FragmentCarrito"
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCompra2())?.commit()
        }

        // Encuentra el botón Atras
        val btnBack = view.findViewById<ImageButton>(R.id.btn_back)
        // Configura el listener para el botón "Atras"
        btnBack.setOnClickListener {
            // Reemplaza el fragmento actual con el fragmento "FragmentCompra2"
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCarrito())?.commit()
        }
    }
}
