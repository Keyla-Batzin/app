package com.example.bloom.macetasaccesorios

import com.example.bloom.base.ProductoBase

data class MacetaAccesorio(
    val id: Int,
    override val nombre: String,
    override val precio: Float,
    override val url: String
) : ProductoBase