package com.example.bloom.compra

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.Mensaje
import com.example.bloom.R
import com.example.bloom.pantallacompra.FragmentCompra1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class CompraStats(
    val compraId: Int,
    var precio: Float = 0f
)

class ComprasFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CompraAdapter
    private lateinit var precioTotalTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compras, container, false)

        rv = view.findViewById(R.id.recyclerCompras)
        rv.layoutManager = GridLayoutManager(requireContext(), 1) // 1 columna

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = CompraAdapter(
            mutableListOf(),
            onDeleteClick = { compra -> eliminarCompra(compra) },
            onSumaClick = { compra -> sumaCantidad(compra) },  // Función para sumar cantidad
            onRestaClick = { compra -> restaCantidad(compra) }  // Función para restar cantidad
        )
        rv.adapter = adapter

        // Encuentra el TextView para el precio total
        precioTotalTextView = view.findViewById(R.id.precioTotal)

        // Cargar datos
        actualizaCompras()
        actualizaPrecioTotal()

        // Encuentra el botón en el layout inflado
        val button = view.findViewById<Button>(R.id.btnComprar)

        // Configura el listener para el botón
        button.setOnClickListener {
            // Cambiar a otro fragmento (FragmentCompra1)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCompra1())?.commit()
        }

        return view
    }

    private fun actualizaCompras() {
        val service = CompraAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaCompras = service.obtenerTodasCompras()

                if (!listaCompras.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        adapter.updateList(listaCompras)
                    }
                } else {
                    Log.e("API", "Lista de compras vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun actualizaPrecioTotal() {
        val service = CompraAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = service.obtenerPrecioTotal()
                withContext(Dispatchers.Main) {
                    precioTotalTextView.text = "Precio Total: ${response.precio_total} €"
                }
                actualizaPrecioTotal()
            } catch (e: Exception) {
                Log.e("API", "Error al obtener el precio total", e)
            }
        }
    }

    private fun eliminarCompra(compra: Compra) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = CompraAPI.API().eliminarCompra(compra.id)
                withContext(Dispatchers.Main) {
                    val position = adapter.comprasList.indexOfFirst { it.id == compra.id }
                    if (position != -1) {
                        adapter.removeItem(position)
                    }
                    Log.d("API", "Compra eliminada: ${response.message}")
                    Mensaje.mostrarPersonalizado(
                        view,
                        mensaje = "❌ Producto eliminado",
                        colorRes = R.color.morado_claro
                    )
                }
                actualizaPrecioTotal()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al eliminar la compra: ${e.message}")
                }
            }
        }
    }

    private fun sumaCantidad(compra: Compra) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = CompraAPI.API().sumaCantidad(compra.id)
                withContext(Dispatchers.Main) {
                    // Actualizar la lista después de sumar la cantidad
                    actualizaCompras()
                    Log.d("API", "Cantidad sumada: ${response.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al sumar cantidad: ${e.message}")
                }
            }
        }
    }

    private fun restaCantidad(compra: Compra) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = CompraAPI.API().restaCantidad(compra.id)
                withContext(Dispatchers.Main) {
                    // Actualizar la lista después de restar la cantidad
                    actualizaCompras()
                    Log.d("API", "Cantidad restada: ${response.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al restar cantidad: ${e.message}")
                }
            }
        }
    }
}