package com.example.bloom.pantallahome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.productos.ProductoAPI
import com.example.bloom.productos.ProductoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    /*
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        rv = view.findViewById(R.id.recyclerProductos)
        rv.layoutManager = GridLayoutManager(requireContext(), 3) // 3 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = ProductoAdapter(emptyList())
        rv.adapter = adapter

        // Cargar datos
        actualizaProductos()

        // Gestionar el SearchView

        return view
    }

    private fun actualizaProductos() {
        val service = ProductoAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaProductos = service.obtenerTodosProductos()

                // Verificar si la lista no es nula
                if (!listaProductos.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = ProductoAdapter(listaProductos)
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de productos vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

     */
}