package com.example.bloom.favoritos

import FavoritoAPI
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.Mensaje
import com.example.bloom.R
import com.example.bloom.compra.Compra
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritoFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: FavoritoAdapter
    private val listaFavoritos = mutableListOf<Favorito>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)

        rv = view.findViewById(R.id.recyclerFavorito)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = FavoritoAdapter(
            listaFavoritos,
            onAddClick = { favorito -> añadirACompra(favorito) },
            onDeleteClick = { favorito -> eliminarFavorito(favorito) }
        )

        rv.adapter = adapter

        actualizaFavoritos()

        return view
    }

    private fun actualizaFavoritos() {
        val service = FavoritoAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val favoritosObtenidos = service.obtenerTodosFavoritos()
                withContext(Dispatchers.Main) {
                    listaFavoritos.clear()
                    listaFavoritos.addAll(favoritosObtenidos)
                    withContext(Dispatchers.Main) {
                        adapter.updateList(favoritosObtenidos)
                    }

                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun añadirACompra(favorito: Favorito) {
        val nuevaCompra = Compra(
            id = 0,
            nombre = favorito.nombre,
            precio = favorito.precio,
            url = favorito.url,
            cantidad = 1
        )

        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = FavoritoAPI.API().crearCompra(nuevaCompra)
                withContext(Dispatchers.Main) {
                    Log.d("API", "Añadido a Compra: ${response.message}")
                    Mensaje.mostrarCesta(view)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al añadir a Compra: ${e.message}")
                }
            }
        }
    }

    private fun eliminarFavorito(favorito: Favorito) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = FavoritoAPI.API().eliminarFavorito(favorito.id)
                withContext(Dispatchers.Main) {
                    val position = listaFavoritos.indexOfFirst { it.id == favorito.id }
                    if (position != -1) {
                        listaFavoritos.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                    Log.d("API", "Favorito eliminado: ${response.message}")

                    Mensaje.mostrarPersonalizado(
                        view,
                        mensaje = "❌ Producto eliminado de favoritos!",
                        colorRes = R.color.morado_claro
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al eliminar favorito: ${e.message}")
                }
            }
        }
    }
}