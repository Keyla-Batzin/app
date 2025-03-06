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
        rv.layoutManager = GridLayoutManager(requireContext(), 3) // 3 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = MacetaAccesorioAdapter(emptyList())
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
                        adapter = MacetaAccesorioAdapter(listaMacetas)
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
}