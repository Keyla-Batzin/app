package com.example.bloom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.compra.Compra
import com.example.bloom.floreseventos.FloresEventos
import com.example.bloom.floreseventos.FloresEventosAPI
import com.example.bloom.floreseventos.FloresEventosAdapter
import com.example.bloom.plantasexterior.PlantaExterior
import com.example.bloom.plantasexterior.PlantaExteriorAPI
import com.example.bloom.plantasexterior.PlantaExteriorAdapter
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
        adapter = FloresEventosAdapter(emptyList()) { florEvento ->
            añadirACompra(florEvento)
        }
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
                        adapter = FloresEventosAdapter(listaFlores) { florEvento ->
                            añadirACompra(florEvento)
                        }
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
}