package com.example.bloom.plantasinterior

import com.example.bloom.base.ProductoBase

data class PlantaInterior (
    val id: Int,
    override val nombre: String,
    override val precio: Float,
    override val url: String
) : ProductoBase