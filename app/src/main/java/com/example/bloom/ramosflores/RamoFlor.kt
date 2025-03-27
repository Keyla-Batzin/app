package com.example.bloom.ramosflores

import com.example.bloom.base.ProductoBase

data class RamoFlor(
    val id: Int,
    override val nombre: String,
    override val precio: Float,
    override val url: String
) : ProductoBase