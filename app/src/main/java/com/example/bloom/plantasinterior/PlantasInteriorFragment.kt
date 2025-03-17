package com.example.bloom.plantasinterior

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

class PlantasInteriorFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: PlantaInteriorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plantas_interior, container, false)

        rv = view.findViewById(R.id.recyclerPlantasInterior)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = PlantaInteriorAdapter(emptyList(),
            onAddClick = { plantaInterior -> añadirACompra(plantaInterior) },  // Función para añadir a Compra
            onAddFavClick = { plantaInterior -> añadirAFavoritos(plantaInterior) }  // Función para añadir a Favoritos
        )
        rv.adapter = adapter

        // Cargar datos
        actualizaPlantasInterior()

        return view
    }

    private fun actualizaPlantasInterior() {
        val service = PlantaInteriorAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaPlantas = service.obtenerTodasPlantasInterior()

                // Verificar si la lista no es nula
                if (!listaPlantas.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = PlantaInteriorAdapter(listaPlantas,
                            onAddClick = { plantaInterior -> añadirACompra(plantaInterior) },
                            onAddFavClick = { plantaInterior -> añadirAFavoritos(plantaInterior) }
                        )
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de plantas de interior vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun añadirACompra(plantaInterior: PlantaInterior) {
        // Crear un objeto Compra con los datos del RamoFlor
        val nuevaCompra = Compra(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = plantaInterior.nombre,
            precio = plantaInterior.precio,  // Asegúrate de que sea una cadena de texto
            url = plantaInterior.url,
            cantidad = 1
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = PlantaInteriorAPI.API().crearCompra(nuevaCompra)
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

    private fun añadirAFavoritos(plantaInterior: PlantaInterior) {
        // Crear un objeto Favorito con los datos
        val nuevoFavorito = Favorito(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = plantaInterior.nombre,
            precio = plantaInterior.precio,
            url = plantaInterior.url
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevoFavorito)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = PlantaInteriorAPI.API().crearFavorito(nuevoFavorito)
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