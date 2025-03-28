package com.example.bloom.plantasinterior

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


class PlantasInteriorFragment : ProductoBaseFragment<PlantaInterior, PlantaInteriorAdapter>() {

    override fun getLayoutResource(): Int = R.layout.fragment_plantas_interior
    override fun getRecyclerViewId(): Int = R.id.recyclerPlantasInterior
    override fun createAdapter(items: List<PlantaInterior>): PlantaInteriorAdapter {
        return PlantaInteriorAdapter(items, { ramoFlor -> addToCart(ramoFlor) }, { ramoFlor -> addToFavorites(ramoFlor) })
    }

    override suspend fun fetchData(): List<PlantaInterior> {
        val service = PlantaInteriorAPI().getAPI()
        return service.obtenerTodasPlantasInterior()
    }

    override fun addToCart(item: PlantaInterior) {
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
                val response = PlantaInteriorAPI().getAPI().crearCompra(nuevaCompra)
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

    override fun addToFavorites(item: PlantaInterior) {
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
                val response = PlantaInteriorAPI().getAPI().crearFavorito(nuevoFavorito)
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