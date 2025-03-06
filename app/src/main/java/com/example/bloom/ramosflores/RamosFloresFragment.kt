package com.example.bloom.ramosflores

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
import com.example.bloom.databinding.ActivityMainBinding
import com.example.bloom.navigation.Fragment_Header
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RamosFloresFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: RamoFlorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ramos_flores, container, false)

        rv = view.findViewById(R.id.recyclerRamosFlores)
        rv.layoutManager = GridLayoutManager(requireContext(), 3)

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = RamoFlorAdapter(emptyList()) { ramoFlor ->
            // Lógica para añadir el ítem a la tabla Compra
            añadirACompra(ramoFlor)
        }
        rv.adapter = adapter

        // Cargar datos
        actualitzaRamosFlores()

        return view
    }

    private fun actualitzaRamosFlores() {
        val service = RamoFlorAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val llistaRamos = service.obtenerTodosRamoFlor()

                // Verificar si la lista no es nula
                if (!llistaRamos.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = RamoFlorAdapter(llistaRamos) { ramoFlor ->
                            añadirACompra(ramoFlor)
                        }
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de ramos vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun añadirACompra(ramoFlor: RamoFlor) {
        // Crear un objeto Compra con los datos del RamoFlor
        val nuevaCompra = Compra(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = ramoFlor.nombre,
            precio = ramoFlor.precio,  // Asegúrate de que sea una cadena de texto
            url = ramoFlor.url
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RamoFlorAPI.API().crearCompra(nuevaCompra)
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
