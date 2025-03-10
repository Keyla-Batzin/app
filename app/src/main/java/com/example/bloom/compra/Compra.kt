package com.example.bloom.compra

data class Compra(
    val id: Int = 0,  // Añade el campo id con un valor por defecto
    val nombre: String,
    val precio: Float,  // Cambia el tipo a Float
    val cantidad: Int,  // Añade el campo cantidad
    val url: String?    // URL de la imagen (puede ser nula)
)