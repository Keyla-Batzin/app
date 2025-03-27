package com.example.bloom.pantallahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bloom.floreseventos.FloresEventosFragment
import com.example.bloom.R
import com.example.bloom.categorias.CategoriasFragment
import com.example.bloom.macetasaccesorios.MacetasAccesoriosFragment
import com.example.bloom.plantasinterior.PlantasInteriorFragment
import com.example.bloom.ramosflores.RamoFlorAdapter
import com.example.bloom.ramosflores.RamosFloresFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        // ------------------- CardViews -----------------------------//
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

        // ------------------- Boton -----------------------------//
        // Configurar botón para navegar a CategoriasFragment
        val btnCat = view.findViewById<Button>(R.id.btn_cat)
        btnCat.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, CategoriasFragment(), "TAG_CATEGORIAS_FRAGMENT")
                .addToBackStack(null)
                .commit()
        }

        // ------------------- Newsletter -----------------------------//
        // Configurar botón para mostrar un Toast
        val btnSuscribirse = view.findViewById<Button>(R.id.btn_suscribirse)
        btnSuscribirse.setOnClickListener {
            Toast.makeText(requireContext(), "Suscripción realizada con éxito.", Toast.LENGTH_SHORT).show()
        }

        // ------------------- Spinner -----------------------------//
        val categorySpinner: Spinner = view.findViewById(R.id.catSpinner)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categorias_array, // Asegúrate de definir este array en strings.xml
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        // Botón de búsqueda
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)

        // Textos e imagen
        val name: TextView = view.findViewById(R.id.nombre)
        val photo: ImageView = view.findViewById(R.id.imagen)
        val precio: TextView = view.findViewById(R.id.precio)

        btnSearch.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()

            // Asigna un rango de IDs basado en la categoría
            val categoryRanges = mapOf(
                "Ramos Flores" to (1..10),
                "Plantas Interior" to (1..5),
                "Plantas Exterior" to (1..5),
                "Flores Eventos" to (1..5),
                "Macetas y Accesorios" to (1..5),
                "Packs" to (1..5)
            )

            val idRange = categoryRanges[selectedCategory] ?: (1..10) // Rango por defecto
            val randomId = idRange.random()

            // Llamar al API
            /*
            val service = RamoFlorAPI().getAPI()
            lifecycleScope.launch {
                try {
                    // Asegúrate de que obtenerRamoFlores esté definido en RamoFlorService
                    val ramo = service.obtenerPorId(randomId)

                    // Actualizar UI
                    withContext(Dispatchers.Main) {
                        name.text = ramo.nombre
                        precio.text = "${ramo.precio}€"

                        Glide.with(photo.context)
                            .load(ramo.url)
                            .placeholder(R.drawable.logo_peque)
                            .error(R.drawable.img_error)
                            .into(photo)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show()
                    }
                }
            }

             */


            val cardProducto = view.findViewById<CardView>(R.id.cardProducto)
            cardProducto.visibility = View.VISIBLE  // Hace que el CardView sea visible
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cancela las corrutinas si es necesario
        viewLifecycleOwner.lifecycleScope.cancel()
    }
}
