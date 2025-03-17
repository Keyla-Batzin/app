package com.example.bloom.plantasexterior

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
import com.example.bloom.ramosflores.RamoFlor
import com.example.bloom.ramosflores.RamoFlorAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlantasExteriorFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: PlantaExteriorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plantas_exterior, container, false)

        rv = view.findViewById(R.id.recyclerPlantasExterior)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = PlantaExteriorAdapter(emptyList(),
            onAddClick = { plantaExterior -> añadirACompra(plantaExterior) },  // Función para añadir a Compra
            onAddFavClick = { plantaExterior  -> añadirAFavoritos(plantaExterior) }  // Función para añadir a Favoritos
        )
        rv.adapter = adapter

        // Cargar datos
        actualizaPlantasExterior()

        return view
    }

    private fun actualizaPlantasExterior() {
        val service = PlantaExteriorAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaPlantas = service.obtenerTodasPlantasExterior()

                // Verificar si la lista no es nula
                if (!listaPlantas.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = PlantaExteriorAdapter(listaPlantas,
                            onAddClick = { plantaExterior -> añadirACompra(plantaExterior) },
                            onAddFavClick = { plantaExterior -> añadirAFavoritos(plantaExterior) }
                        )
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de plantas de exterior vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun añadirACompra(plantaExterior: PlantaExterior) {
        // Crear un objeto Compra con los datos del RamoFlor
        val nuevaCompra = Compra(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = plantaExterior.nombre,
            precio = plantaExterior.precio,  // Asegúrate de que sea una cadena de texto
            url = plantaExterior.url,
            cantidad = 1
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = PlantaExteriorAPI.API().crearCompra(nuevaCompra)
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

    private fun añadirAFavoritos(plantaExterior: PlantaExterior) {
        // Crear un objeto Favorito con los datos
        val nuevoFavorito = Favorito(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = plantaExterior.nombre,
            precio = plantaExterior.precio,
            url = plantaExterior.url
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevoFavorito)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = PlantaExteriorAPI.API().crearFavorito(nuevoFavorito)
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