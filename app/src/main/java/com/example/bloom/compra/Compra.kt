package com.example.bloom.compra

data class Compra(
    val id: Int = 0,  // Añade el campo id con un valor por defecto
    val nombre: String,
    val precio: String,  // Asegúrate de que sea String
    val url: String?
)