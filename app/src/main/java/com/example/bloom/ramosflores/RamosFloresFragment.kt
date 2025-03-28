package com.example.bloom.ramosflores

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import com.google.android.material.snackbar.Snackbar

class RamosFloresFragment : ProductoBaseFragment<RamoFlor, RamoFlorAdapter>() {

    override fun getLayoutResource(): Int = R.layout.fragment_ramos_flores
    override fun getRecyclerViewId(): Int = R.id.recyclerRamosFlores
    override fun createAdapter(items: List<RamoFlor>): RamoFlorAdapter {
        return RamoFlorAdapter(items, { ramoFlor -> addToCart(ramoFlor) }, { ramoFlor -> addToFavorites(ramoFlor) })
    }

    override suspend fun fetchData(): List<RamoFlor> {
        val service = RamoFlorAPI().getAPI()
        return service.obtenerTodosRamosFlores()
    }

    override fun addToCart(item: RamoFlor) {
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
                val response = RamoFlorAPI().getAPI().crearCompra(nuevaCompra)
                withContext(Dispatchers.Main) {
                    Log.d("API", "Añadido a Compra: ${response.message}")
                    // Mostrar Snackbar
                    Mensaje.mostrarCesta(view)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API", "Error al añadir a Compra: ${e.message}")
                }
            }
        }
    }

    override fun addToFavorites(item: RamoFlor) {
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
                val response = RamoFlorAPI().getAPI().crearFavorito(nuevoFavorito)
                withContext(Dispatchers.Main) {
                    Log.d("API", "Añadido a Favoritos: ${response.message}")
                    // Mostrar Snackbar
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
