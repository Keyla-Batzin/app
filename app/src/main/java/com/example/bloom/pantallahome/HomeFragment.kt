package com.example.bloom.pantallahome

import android.os.Bundle
import android.view.Gravity
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bloom.floreseventos.FloresEventosFragment
import com.example.bloom.R
import com.example.bloom.categorias.CategoriasFragment
import com.example.bloom.floreseventos.FlorEvento
import com.example.bloom.floreseventos.FlorEventoAPI
import com.example.bloom.macetasaccesorios.MacetaAccesorio
import com.example.bloom.macetasaccesorios.MacetaAccesorioAPI
import com.example.bloom.macetasaccesorios.MacetasAccesoriosFragment
import com.example.bloom.pack.Pack
import com.example.bloom.pack.PackAPI
import com.example.bloom.plantasexterior.PlantaExterior
import com.example.bloom.plantasexterior.PlantaExteriorAPI
import com.example.bloom.plantasinterior.PlantaInterior
import com.example.bloom.plantasinterior.PlantaInteriorAPI
import com.example.bloom.plantasinterior.PlantasInteriorFragment
import com.example.bloom.ramosflores.RamoFlor
import com.example.bloom.ramosflores.RamoFlorAPI
import com.example.bloom.ramosflores.RamosFloresFragment
import com.google.android.material.snackbar.Snackbar
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
            view?.let { rootView ->
                val snackbar = Snackbar.make(
                    rootView,
                    "✅ Subscripción realizada con éxito",
                    Snackbar.LENGTH_LONG
                ).apply {
                    setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                    context?.let { ctx ->
                        setBackgroundTint(ContextCompat.getColor(ctx, R.color.lila_azul))
                    }
                }

                // Personalización: Centrar texto
                snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }

                snackbar.show()
            }
        }

        // ------------------- Spinner -----------------------------//
        val categorySpinner: Spinner = view.findViewById(R.id.catSpinner)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categorias_array, // Definir este array en strings.xml
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

        // Mapeo de categorías a servicios y métodos
        val serviceMethodMap: Map<String, suspend (Int) -> Any> = mapOf(
            "Ramos Flores" to { id -> RamoFlorAPI().getAPI().obtenerRamoFlor(id) },
            "Plantas Interior" to { id -> PlantaInteriorAPI().getAPI().obtenerPlantaInterior(id) },
            "Plantas Exterior" to { id -> PlantaExteriorAPI().getAPI().obtenerPlantaExterior(id) },
            "Flores Eventos" to { id -> FlorEventoAPI().getAPI().obtenerFloresEventos(id) },
            "Macetas y Accesorios" to { id -> MacetaAccesorioAPI().getAPI().obtenerMacetaAccesorio(id) },
            "Packs" to { id -> PackAPI().getAPI().obtenerPack(id) }
        )

        btnSearch.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            val cardProducto = view.findViewById<CardView>(R.id.cardProducto)
            cardProducto.visibility = View.VISIBLE  // Hace que el CardView sea visible

            // Definir rangos de ID según la categoría
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

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val item = when (selectedCategory) {
                        "Ramos Flores" -> RamoFlorAPI().getAPI().obtenerRamoFlor(randomId)
                        "Plantas Interior" -> PlantaInteriorAPI().getAPI().obtenerPlantaInterior(randomId)
                        "Plantas Exterior" -> PlantaExteriorAPI().getAPI().obtenerPlantaExterior(randomId)
                        "Flores Eventos" -> FlorEventoAPI().getAPI().obtenerFloresEventos(randomId)
                        "Macetas y Accesorios" -> MacetaAccesorioAPI().getAPI().obtenerMacetaAccesorio(randomId)
                        "Packs" -> PackAPI().getAPI().obtenerPack(randomId)
                        else -> null
                    }

                    if (item == null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Categoría no válida", Toast.LENGTH_SHORT).show()
                        }
                        return@launch
                    }

                    // Actualizar UI en el hilo principal
                    withContext(Dispatchers.Main) {
                        when (item) {
                            is RamoFlor -> {
                                name.text = item.nombre
                                precio.text = "${item.precio}€"
                                Glide.with(photo.context).load(item.url).placeholder(R.drawable.logo_peque).error(R.drawable.img_error).into(photo)
                            }
                            is PlantaInterior -> {
                                name.text = item.nombre
                                precio.text = "${item.precio}€"
                                Glide.with(photo.context).load(item.url).placeholder(R.drawable.logo_peque).error(R.drawable.img_error).into(photo)
                            }
                            is PlantaExterior -> {
                                name.text = item.nombre
                                precio.text = "${item.precio}€"
                                Glide.with(photo.context).load(item.url).placeholder(R.drawable.logo_peque).error(R.drawable.img_error).into(photo)
                            }
                            is FlorEvento -> {
                                name.text = item.nombre
                                precio.text = "${item.precio}€"
                                Glide.with(photo.context).load(item.url).placeholder(R.drawable.logo_peque).error(R.drawable.img_error).into(photo)
                            }
                            is MacetaAccesorio -> {
                                name.text = item.nombre
                                precio.text = "${item.precio}€"
                                Glide.with(photo.context).load(item.url).placeholder(R.drawable.logo_peque).error(R.drawable.img_error).into(photo)
                            }
                            is Pack -> {
                                name.text = item.nombre
                                precio.text = "${item.precio}€"
                                Glide.with(photo.context).load(item.url).placeholder(R.drawable.logo_peque).error(R.drawable.img_error).into(photo)
                            }

                            else -> {}
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Cancela las corrutinas si es necesario
        viewLifecycleOwner.lifecycleScope.cancel()
    }
}
