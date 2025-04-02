package com.example.bloom.compra

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.Mensaje
import com.example.bloom.R
import com.example.bloom.stats.SharedViewModel
import com.example.bloom.pantallacompra.FragmentCompra1
import com.example.bloom.stats.CompraStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException


class ComprasFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CompraAdapter
    private lateinit var precioTotalTextView: TextView
    private lateinit var viewModel: SharedViewModel
    private val compraStatsList = mutableListOf<CompraStats>()
    private var precioCompra : Double = 0.0
    private var idCompra : Int = 0
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compras, container, false)

        rv = view.findViewById(R.id.recyclerCompras)
        rv.layoutManager = GridLayoutManager(requireContext(), 1)

        adapter = CompraAdapter(
            mutableListOf(),
            onDeleteClick = { compra -> eliminarCompra(compra) },
            onSumaClick = { compra -> sumaCantidad(compra) },
            onRestaClick = { compra -> restaCantidad(compra) }
        )
        rv.adapter = adapter

        precioTotalTextView = view.findViewById(R.id.precioTotal)

        // 🔹 Inicializa el ViewModel antes de acceder a compraStats
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        actualizaCompras()
        actualizaPrecioTotal()

        val button = view.findViewById<Button>(R.id.btnComprar)
        button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) { // Se cancela solo si el Fragment deja de estar STARTED
                    try {
                        val nuevaCompra = CompraStats(idCompra + 1, precioCompra)

                        if (compraStatsList.isEmpty()) {
                            compraStatsList.add(nuevaCompra)
                        }

                        viewModel.compraStats.value = compraStatsList
                    } catch (e: CancellationException) {
                        Log.e("API", "La coroutine fue cancelada antes de completar la tarea")
                    } catch (e: Exception) {
                        Log.e("API", "Error al pasar Compra a Stats", e)
                    }
                }
            }


        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)
            ?.replace(R.id.main_fragment, FragmentCompra1())?.commit()
        }

        return view
    }


    private fun actualizaCompras() {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
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
            while (isActive) { // Evita ejecutar si la coroutine es cancelada
                try {
                    val response = service.obtenerPrecioTotal()
                    withContext(Dispatchers.Main) {
                        precioTotalTextView.text = "Precio Total: ${response.precio_total} €"
                        precioCompra = response.precio_total.toDouble()
                    }
                } catch (e: CancellationException) {
                    Log.e("API", "La coroutine Precio Total fue cancelada antes de completar la tarea")
                    break
                } catch (e: Exception) {
                    Log.e("API", "Error al obtener el precio total", e)
                }
                delay(5000) // Espera 5 segundos antes de volver a ejecutar
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

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel() // Cancela todas las coroutines activas
    }

}