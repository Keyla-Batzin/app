package com.example.bloom;

class CategoriaProvider {
    companion object {
        val categoriasList = listOf(
                Categoria(
                        nombre = "Ramos",
                        imagen = R.drawable.ramo_cat.toString()
                ),
                Categoria(
                        nombre = "Macetas",
                        imagen = R.drawable.maceta_cat.toString()
                )
        )
    }
}
