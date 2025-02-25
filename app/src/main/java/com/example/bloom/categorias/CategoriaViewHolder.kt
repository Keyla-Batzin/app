package com.example.bloom.categorias

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class CategoriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val photo = view.findViewById<ImageView>(R.id.imgCat)
    val categoria = view.findViewById<TextView>(R.id.nombreCat)

    fun render(categoriaModel: Categoria) {
        categoria.text = categoriaModel.nombre
        photo.setImageResource(categoriaModel.photo) // Asigna la imagen dinámica.
    }
}
