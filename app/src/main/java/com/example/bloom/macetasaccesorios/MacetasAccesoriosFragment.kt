package com.example.bloom.macetasaccesorios

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import com.example.bloom.plantasexterior.PlantaExterior
import com.example.bloom.plantasexterior.PlantaExteriorAPI
import com.example.bloom.plantasexterior.PlantaExteriorAdapter
import com.example.bloom.ramosflores.RamoFlor
import com.example.bloom.ramosflores.RamoFlorAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MacetasAccesoriosFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: MacetaAccesorioAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_macetas_accesorios, container, false)

        rv = view.findViewById(R.id.recyclerMacetasAccesorios)
        rv.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = MacetaAccesorioAdapter(emptyList(),
            onAddClick = { macetaAcesorio -> añadirACompra(macetaAcesorio) },  // Función para añadir a Compra
            onAddFavClick = { macetaAcesorio -> añadirAFavoritos(macetaAcesorio) }  // Función para añadir a Favoritos
        )
        rv.adapter = adapter

        // Cargar datos
        actualizaMacetasAccesorios()

        return view
    }

    private fun actualizaMacetasAccesorios() {
        val service = MacetaAccesorioAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaMacetas = service.obtenerTodasMacetasAccesorios()

                // Verificar si la lista no es nula
                if (!listaMacetas.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = MacetaAccesorioAdapter(listaMacetas,
                            onAddClick = { macetaAcesiorio -> añadirACompra(macetaAcesiorio) },
                            onAddFavClick = { macetaAcesiorio -> añadirAFavoritos(macetaAcesiorio) }
                        )
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de macetas y accesorios vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun añadirACompra(macetaAcesiorio: MacetaAccesorio) {
        // Crear un objeto Compra con los datos del RamoFlor
        val nuevaCompra = Compra(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = macetaAcesiorio.nombre,
            precio = macetaAcesiorio.precio,  // Asegúrate de que sea una cadena de texto
            url = macetaAcesiorio.url,
            cantidad = 1
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = MacetaAccesorioAPI.API().crearCompra(nuevaCompra)
                withContext(Dispatchers.Main) {
                    // Mostrar un mensaje de éxito
                    Log.d("API", "Añadido a Compra: ${response.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Mostrar un mensaje de error
                    Log.e("API", "Error al añadir a Compra: ${e.message}")
                }
            }
        }
    }

    private fun añadirAFavoritos(macetaAcesiorio: MacetaAccesorio) {
        // Crear un objeto Favorito con los datos del RamoFlor
        val nuevoFavorito = Favorito(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = macetaAcesiorio.nombre,
            precio = macetaAcesiorio.precio,
            url = macetaAcesiorio.url
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevoFavorito)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = MacetaAccesorioAPI.API().crearFavorito(nuevoFavorito)
                withContext(Dispatchers.Main) {
                    // Mostrar un mensaje de éxito
                    Log.d("API", "Añadido a Favoritos: ${response.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Mostrar un mensaje de error
                    Log.e("API", "Error al añadir a Favoritos: ${e.message}")
                }
            }
        }
    }
}