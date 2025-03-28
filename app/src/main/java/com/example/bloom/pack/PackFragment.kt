package com.example.bloom.pack

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.bloom.Mensaje
import com.example.bloom.R
import com.example.bloom.base.ProductoBaseFragment
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PackFragment : ProductoBaseFragment<Pack, PackAdapter>() {

    override fun getLayoutResource(): Int = R.layout.fragment_pack
    override fun getRecyclerViewId(): Int = R.id.recyclerPack
    override fun createAdapter(items: List<Pack>): PackAdapter {
        return PackAdapter(items, { pack -> addToCart(pack) }, { pack -> addToFavorites(pack) })
    }

    override suspend fun fetchData(): List<Pack> {
        val service = PackAPI().getAPI()
        return service.obtenerTodosPacks()
    }

    override fun addToCart(item: Pack) {
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
                val response = PackAPI().getAPI().crearCompra(nuevaCompra)
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

    override fun addToFavorites(item: Pack) {
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
                val response = PackAPI().getAPI().crearFavorito(nuevoFavorito)
                withContext(Dispatchers.Main) {
                    Log.d("API", "Añadido a Favoritos: ${response.message}")
                    Mensaje.mostrarFavoritos(view)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al añadir a Favoritos: ${e.message}")
                }
            }
        }
    }
}