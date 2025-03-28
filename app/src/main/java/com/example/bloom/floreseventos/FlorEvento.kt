package com.example.bloom.floreseventos

import com.example.bloom.base.ProductoBase

data class FlorEvento(
    val id: Int,
    override val nombre: String,
    override val precio: Float,
    override val url: String
) : ProductoBase