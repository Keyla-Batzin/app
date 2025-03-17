package com.example.bloom.floreseventos

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
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FloresEventosFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: FloresEventosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flores_eventos, container, false)

        rv = view.findViewById(R.id.recyclerFloresEventos)
        rv.layoutManager = GridLayoutManager(requireContext(), 2) // 3 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = FloresEventosAdapter(emptyList(),
            onAddClick = { florEvento -> añadirACompra(florEvento) },  // Función para añadir a Compra
            onAddFavClick = { florEvento -> añadirAFavoritos(florEvento) }  // Función para añadir a Favoritos
        )
        rv.adapter = adapter

        // Cargar datos
        actualizaFloresEventos()

        return view
    }

    private fun actualizaFloresEventos() {
        val service = FloresEventosAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaFlores = service.obtenerTodasFloresEventos()

                // Verificar si la lista no es nula
                if (!listaFlores.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = FloresEventosAdapter(listaFlores,
                            onAddClick = { florEvento -> añadirACompra(florEvento) },
                            onAddFavClick = { florEvento -> añadirAFavoritos(florEvento) }
                        )
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de flores para eventos vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun añadirACompra(florEvento: FloresEventos) {
        // Crear un objeto Compra con los datos del RamoFlor
        val nuevaCompra = Compra(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = florEvento.nombre,
            precio = florEvento.precio,  // Asegúrate de que sea una cadena de texto
            url = florEvento.url,
            cantidad = 1
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = FloresEventosAPI.API().crearCompra(nuevaCompra)
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

    private fun añadirAFavoritos(florEvento: FloresEventos) {
        // Crear un objeto Favorito con los datos del RamoFlor
        val nuevoFavorito = Favorito(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = florEvento.nombre,
            precio = florEvento.precio,
            url = florEvento.url
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevoFavorito)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = FloresEventosAPI.API().crearFavorito(nuevoFavorito)
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