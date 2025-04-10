package com.example.bloom.plantasexterior

import com.example.bloom.base.ProductoBase

data class PlantaExterior(
    val id: Int,
    override val nombre: String,
    override val precio: Float,
    override val url: String
) : ProductoBase