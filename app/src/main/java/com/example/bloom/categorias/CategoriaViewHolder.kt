package com.example.bloom.categorias

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class CategoriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.nombreCat)
    val photo = view.findViewById<ImageView>(R.id.imgCat)

    fun render(categoriaModel: Categoria) {
        name.text = categoriaModel.nombre

        Log.d("ImageURL", "Cargando imagen desde: ${categoriaModel.url}")
        Glide.with(photo.context)
            .load(categoriaModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}
