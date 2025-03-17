package com.example.bloom.pantallahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.bloom.floreseventos.FloresEventosFragment
import com.example.bloom.R
import com.example.bloom.categorias.CategoriasFragment
import com.example.bloom.macetasaccesorios.MacetasAccesoriosFragment
import com.example.bloom.plantasinterior.PlantasInteriorFragment
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
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, RamosFloresFragment(), "TAG_RAMOS_FLORES_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        val cardPlant = view.findViewById<CardView>(R.id.cardPlanta)
        cardPlant.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, PlantasInteriorFragment(), "TAG_PLANTAS_INTERIOR_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        val cardPlantInt = view.findViewById<CardView>(R.id.cardEventos)
        cardPlantInt.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, FloresEventosFragment(), "TAG_FLORES_EVENTOS_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        val cardMaceta = view.findViewById<CardView>(R.id.cardMaceta)
        cardMaceta.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, MacetasAccesoriosFragment(), "TAG_MACETAS_ACCESORIOS_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        // Configurar botón para navegar a CategoriasFragment
        val btnCat = view.findViewById<Button>(R.id.btn_cat)
        btnCat.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, CategoriasFragment(), "TAG_CATEGORIAS_FRAGMENT")
                .addToBackStack(null)
                .commit()
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
