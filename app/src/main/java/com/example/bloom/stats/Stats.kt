package com.example.bloom.stats

import android.app.Application
import android.util.Log
import com.example.bloom.compra.PrecioTotalResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// Los datos vienen de ComprasFragment
data class CompraStats(
    val id : Int,
    val precioTotal : Float
)

// Los datos vienen de CategoriasFragment
data class CategoriaStats(
    val categoriaId: Int,
    var clickCount: Int = 0,
    val categoriaName: String
)

class Stats : Application() {

    val db: FirebaseFirestore by lazy { Firebase.firestore }

    // Método para obtener el contador de inicios de sesión
    fun getLoginCount(callback: (Int) -> Unit) {
        db.collection("stats").document("loginCount")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val loginCount = document.getLong("numLogin")?.toInt() ?: 0
                    callback(loginCount)
                } else {
                    callback(0)
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting login count", e)
                callback(0)
            }
    }

    // Método para guardar estadísticas de categorías
    fun saveCategoriaStats(categoriaStats: List<CategoriaStats>) {
        val categoriaData = hashMapOf<String, Any>()

        categoriaStats.forEach {
            val key = it.categoriaId.toString()
            val value = hashMapOf(
                "categoriaId" to it.categoriaId,
                "clickCount" to it.clickCount,
                "categoriaName" to it.categoriaName
            )
            categoriaData[key] = value
        }

        db.collection("stats").document("categoriaStats")
            .set(categoriaData)
            .addOnSuccessListener {
                Log.d("Firestore", "Categoria stats saved successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error saving categoria stats", e)
            }
    }


    // Método para obtener estadísticas de categorías
    fun getCategoriaStats(callback: (List<CategoriaStats>) -> Unit) {
        db.collection("stats").document("categoriaStats")
            .get()
            .addOnSuccessListener { document ->
                Log.d("Stats", "Documento obtenido: ${document.id}, existe: ${document.exists()}")

                if (document != null && document.exists()) {
                    val result = mutableListOf<CategoriaStats>()
                    val data = document.data

                    // Verificar si hay una lista de categorías
                    val categoriaData = data?.get("categorias") as? List<*>
                    if (categoriaData != null && categoriaData.isNotEmpty()) {
                        // Procesar como lista (formato esperado)
                        try {
                            val categoriaStats = categoriaData.mapNotNull {
                                val cat = it as? Map<*, *> ?: return@mapNotNull null
                                CategoriaStats(
                                    categoriaId = (cat["categoriaId"] as? Number)?.toInt() ?: 0,
                                    clickCount = (cat["clickCount"] as? Number)?.toInt() ?: 0,
                                    categoriaName = cat["categoriaName"] as? String ?: "Desconocido"
                                )
                            }
                            Log.d("Stats", "Categorías encontradas (formato lista): ${categoriaStats.size}")
                            result.addAll(categoriaStats)
                        } catch (e: Exception) {
                            Log.e("Stats", "Error procesando lista de categorías", e)
                        }
                    } else {
                        // Intentar procesar como mapa (formato alternativo)
                        try {
                            data?.entries?.forEach { entry ->
                                // Ignorar la entrada "categorias" si existe y está vacía
                                if (entry.key != "categorias") {
                                    val catValue = entry.value
                                    if (catValue is Map<*, *>) {
                                        // Si el valor es un mapa, extraer los datos
                                        val categoriaId = (catValue["categoriaId"] as? Number)?.toInt()
                                            ?: entry.key.toIntOrNull()
                                            ?: 0

                                        val clickCount = (catValue["clickCount"] as? Number)?.toInt() ?: 0
                                        val categoriaName = catValue["categoriaName"] as? String
                                            ?: "Categoría $categoriaId"

                                        if (clickCount > 0) {
                                            result.add(
                                                CategoriaStats(
                                                    categoriaId = categoriaId,
                                                    clickCount = clickCount,
                                                    categoriaName = categoriaName
                                                )
                                            )
                                        }
                                    } else if (catValue is Number && entry.key.matches(Regex("categoria\\d+"))) {
                                        // Si el valor es un número y la clave es "categoria" seguido de dígitos
                                        val categoriaId = entry.key.replace("categoria", "").toIntOrNull() ?: 0
                                        if (catValue.toInt() > 0) {
                                            result.add(
                                                CategoriaStats(
                                                    categoriaId = categoriaId,
                                                    clickCount = catValue.toInt(),
                                                    categoriaName = "Categoría $categoriaId"
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                            Log.d("Stats", "Categorías encontradas (formato mapa): ${result.size}")
                        } catch (e: Exception) {
                            Log.e("Stats", "Error procesando mapa de categorías", e)
                        }
                    }

                    if (result.isNotEmpty()) {
                        callback(result)
                    } else {
                        // Imprimir datos para depuración
                        Log.w("Stats", "No se encontraron categorías en ningún formato conocido")
                        Log.d("Stats", "Contenido del documento: ${document.data}")
                        callback(emptyList())
                    }
                } else {
                    Log.w("Stats", "El documento no existe o es nulo")
                    callback(emptyList())
                }
            }
            .addOnFailureListener { e ->
                Log.e("Stats", "Error getting categoria stats", e)
                callback(emptyList())
            }
    }

    // Método para obtener estadísticas de compras
    fun getCompraStats(callback: (List<CompraStats>) -> Unit) {
        db.collection("stats").document("compraStats")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val compraData = document.get("compras") as? List<*>
                    val compraStats = compraData?.map {
                        val data = it as Map<*, *>
                        CompraStats(
                            id = (data["categoriaId"] as? Number)?.toInt() ?: 0,
                            precioTotal = (data["precioTotal"] as? Number)?.toFloat() ?: 0.00f,
                        )
                    } ?: emptyList()
                    callback(compraStats)
                } else {
                    callback(emptyList())
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error obteniendo compra stats", e)
                callback(emptyList())
            }
    }



}
