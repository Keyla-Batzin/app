package com.example.bloom.pack

import com.example.bloom.base.ProductoBase

data class Pack(
    val id: Int,
    override val nombre: String,
    override val precio: Float,
    override val url: String
) : ProductoBase