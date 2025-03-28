package com.example.bloom.floreseventos

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.bloom.R
import com.example.bloom.base.ProductoBaseFragment
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FloresEventosFragment : ProductoBaseFragment<FlorEvento, FlorEventoAdapter>() {

    override fun getLayoutResource(): Int = R.layout.fragment_flores_eventos
    override fun getRecyclerViewId(): Int = R.id.recyclerFloresEventos
    override fun createAdapter(items: List<FlorEvento>): FlorEventoAdapter {
        return FlorEventoAdapter(items, { floresEventos -> addToCart(floresEventos) }, { floresEventos -> addToFavorites(floresEventos) })
    }

    override suspend fun fetchData(): List<FlorEvento> {
        val service = FlorEventoAPI().getAPI()
        return service.obtenerTodasFloresEventos()
    }

    override fun addToCart(item: FlorEvento) {
        val nuevaCompra = Compra(
            id = 0,
            nombre = item.nombre,
            precio = item.precio,
            url = item.url,
            cantidad = 1
        )
        val gson = Gson()
        val json = gson.toJson(nuevaCompra)
        Log.d("API", "JSON enviado: $json")

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = FlorEventoAPI().getAPI().crearCompra(nuevaCompra)
                withContext(Dispatchers.Main) {
                    Log.d("API", "Añadido a Compra: ${response.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al añadir a Compra: ${e.message}")
                }
            }
        }
    }

    override fun addToFavorites(item: FlorEvento) {
        val nuevoFavorito = Favorito(
            id = 0,
            nombre = item.nombre,
            precio = item.precio,
            url = item.url
        )
        val gson = Gson()
        val json = gson.toJson(nuevoFavorito)
        Log.d("API", "JSON enviado: $json")

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = FlorEventoAPI().getAPI().crearFavorito(nuevoFavorito)
                withContext(Dispatchers.Main) {
                    Log.d("API", "Añadido a Favoritos: ${response.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al añadir a Favoritos: ${e.message}")
                }
            }
        }
    }
}