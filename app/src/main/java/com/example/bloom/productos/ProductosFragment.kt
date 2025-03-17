package com.example.bloom.productos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import com.example.bloom.ramosflores.RamoFlor
import com.example.bloom.ramosflores.RamoFlorAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductosFragment : Fragment() {
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)

        rv = view.findViewById(R.id.recyclerProductos)
        rv.layoutManager = GridLayoutManager(requireContext(), 1)

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = ProductoAdapter(emptyList(),
            onAddClick = { producto -> añadirACompra(producto) },  // Función para añadir a Compra
            onAddFavClick = { producto -> añadirAFavoritos(producto) }  // Función para añadir a Favoritos
        )
        rv.adapter = adapter

        // Configurar el SearchView
        searchView = view.findViewById(R.id.searchBar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    buscarProductos(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    buscarProductos(newText)
                }
                return true
            }
        })

        // Cargar datos iniciales
        actualizaProductos()

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
                        adapter = ProductoAdapter(listaProductos,
                            onAddClick = { producto -> añadirACompra(producto) },
                            onAddFavClick = { producto -> añadirAFavoritos(producto) }
                        )
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

    private fun buscarProductos(query: String) {
        val service = ProductoAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaProductos = service.buscarProductos(query)

                // Verificar si la lista no es nula
                if (!listaProductos.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = ProductoAdapter(listaProductos,
                            onAddClick = { producto -> añadirACompra(producto) },
                            onAddFavClick = { producto -> añadirAFavoritos(producto) }
                        )
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de productos vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al buscar productos", e)
            }
        }
    }

    private fun añadirACompra(producto: Producto) {
        // Crear un objeto Compra con los datos del RamoFlor
        val nuevaCompra = Compra(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = producto.nombre,
            precio = producto.precio,
            url = producto.url,
            cantidad = 1
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ProductoAPI.API().crearCompra(nuevaCompra)
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

    private fun añadirAFavoritos(producto: Producto) {
        // Crear un objeto Favorito con los datos del RamoFlor
        val nuevoFavorito = Favorito(
            id = 0,  // Añade el campo id con un valor por defecto
            nombre = producto.nombre,
            precio = producto.precio,
            url = producto.url
        )

        // Imprimir el JSON que se enviará
        val gson = Gson()
        val json = gson.toJson(nuevoFavorito)
        Log.d("API", "JSON enviado: $json")

        // Hacer la llamada POST a la API
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ProductoAPI.API().crearFavorito(nuevoFavorito)
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
