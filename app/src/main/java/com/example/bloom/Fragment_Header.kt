package com.example.bloom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class Fragment_Header : Fragment() { // Elimina el guion bajo del nombre para seguir las convenciones de nombres en Kotlin

    private lateinit var toolbar: Toolbar // Declarar el toolbar como lateinit porque se inicializa en onCreateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Indica que este fragmento tiene un menú de opciones
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el diseño para este fragmento
        val view = inflater.inflate(R.layout.fragment_header, container, false) // Cambia el nombre del recurso a "fragment_header" para seguir las convenciones de nombres

        // Configura el Toolbar desde el diseño
        toolbar = view.findViewById(R.id.main_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Infla el menú específico para este fragmento
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}
