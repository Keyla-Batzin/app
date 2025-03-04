package com.example.bloom.categorias

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

class CategoriasFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        rv = view.findViewById(R.id.recyclerCategorias)
        rv.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = CategoriaAdapter(emptyList())
        rv.adapter = adapter

        // Cargar datos
        actualizaCategorias()

        return view
    }

    private fun actualizaCategorias() {
        val service = CategoriaAPI.API() // Cambia a CategoriaAPI
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaCategorias = service.obtenerTodasCategorias() // Cambia a obtenerTodasCategorias

                // Verificar si la lista no es nula
                if (!listaCategorias.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = CategoriaAdapter(listaCategorias)
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de categorías vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }
}