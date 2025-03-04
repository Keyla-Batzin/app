package com.example.bloom.compra

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

class ComprasFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CompraAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compras, container, false)

        rv = view.findViewById(R.id.recyclerCompras)
        rv.layoutManager = GridLayoutManager(requireContext(), 1) // 1 columna

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = CompraAdapter(emptyList()) { compra ->
            // Lógica para eliminar la compra
            eliminarCompra(compra)
        }
        rv.adapter = adapter

        // Cargar datos
        actualizaCompras()

        return view
    }

    private fun actualizaCompras() {
        val service = CompraAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaCompras = service.obtenerTodasCompras()

                // Verificar si la lista no es nula
                if (!listaCompras.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = CompraAdapter(listaCompras) { compra ->
                            eliminarCompra(compra)
                        }
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de compras vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun eliminarCompra(compra: Compra) {
        // Hacer la llamada DELETE a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = CompraAPI.API().eliminarCompra(compra.id)
                withContext(Dispatchers.Main) {
                    // Mostrar un mensaje de éxito
                    Log.d("API", "Compra eliminada: ${response.message}")
                    // Actualizar la lista de compras después de eliminar
                    actualizaCompras()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    actualizaCompras()
                    // Mostrar un mensaje de error
                    Log.e("API", "Error al eliminar la compra: ${e.message}")
                }
            }
        }
    }
}