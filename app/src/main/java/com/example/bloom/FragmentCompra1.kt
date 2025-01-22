package com.example.bloom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class FragmentCompra1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compra1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countrySpinner: Spinner = view.findViewById(R.id.countrySpinner)

        // Crear un adaptador
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries_array, // Array de países
            android.R.layout.simple_spinner_item
        )

        // Configurar el diseño desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al Spinner
        countrySpinner.adapter = adapter

        // Establecer un texto predeterminado usando @string/select_country
        countrySpinner.prompt = getString(R.string.select_country)

    }
}
