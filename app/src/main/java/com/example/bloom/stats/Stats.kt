package com.example.bloom.stats

import android.app.Application

data class CompraStats(
    val idCompra: Int = 0,
    val precio: Double = 0.0
)

data class CategoriaStats(
    val categoriaId: Int,
    var clickCount: Int = 0,
    val categoriaName: String
)

class Stats : Application() {

}