package com.example.bloom;

class CategoriaProvider {
    companion object {
        val categoriasList = listOf(
            Categoria(
                nombre = "Ramos de Flores",
                R.drawable.ramo_cat
            ),
            Categoria(
                nombre = "Plantas de Interior",
                R.drawable.maceta_cat
            ),
            Categoria(
                nombre = "Plantas de Exterior",
                R.drawable.planta_exterior_cat
            ),
            Categoria(
                nombre = "Flores para Eventos",
                R.drawable.flores_eventos_cat
            ),
            Categoria(
                nombre = "Arreglos Florales",
                R.drawable.arreglos_florales_cat
            ),
            Categoria(
                nombre = "Flores para Ocasiones",
                R.drawable.flores_ocasion_cat
            ),
            Categoria(
                nombre = "Macetas y Accesorios",
                R.drawable.macetas_acesorio_cat
            ),
            Categoria(
                nombre = "Packs",
                R.drawable.paquetes_cat
            ),
        )
    }
}
