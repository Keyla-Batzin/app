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
        rv.layoutManager = GridLayoutManager(requireContext(), 3) // 3 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = PlantaInteriorAdapter(emptyList())
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
                        adapter = PlantaInteriorAdapter(listaPlantas)
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
}