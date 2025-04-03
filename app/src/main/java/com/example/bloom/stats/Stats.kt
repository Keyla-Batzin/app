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
        val categoriaData = categoriaStats.map {
            hashMapOf(
                "categoriaId" to it.categoriaId,
                "clickCount" to it.clickCount,
                "categoriaName" to it.categoriaName
            )
        }
        db.collection("stats").document("categoriaStats")
            .set(hashMapOf("categorias" to categoriaData))
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
                if (document != null && document.exists()) {
                    val categoriaData = document.get("categorias") as? List<*>
                    val categoriaStats = categoriaData?.map {
                        val data = it as Map<*, *>
                        CategoriaStats(
                            categoriaId = (data["categoriaId"] as? Long)?.toInt() ?: 0,
                            clickCount = (data["clickCount"] as? Long)?.toInt() ?: 0,
                            categoriaName = data["categoriaName"] as? String ?: ""
                        )
                    } ?: emptyList()
                    callback(categoriaStats)
                } else {
                    callback(emptyList())
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting categoria stats", e)
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
