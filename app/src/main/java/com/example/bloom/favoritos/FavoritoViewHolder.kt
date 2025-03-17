package com.example.bloom.favoritos

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class FavoritoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreFavorito)
    val photo: ImageView = view.findViewById(R.id.imgFavorito)
    val precio: TextView = view.findViewById(R.id.precioFavorito)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd) // Referencia al botón "Añadir"
    val btnFav: ImageButton = view.findViewById(R.id.btnFav)

    fun render(favoritoModel: Favorito, onAddClick: (Favorito) -> Unit, onDeleteClick: (Favorito) -> Unit) {
        name.text = favoritoModel.nombre
        precio.text = buildString {
            append(favoritoModel.precio.toString())
            append("€")
        }

        Log.d("ImageURL", "Cargando imagen desde: ${favoritoModel.url}")
        Glide.with(photo.context)
            .load(favoritoModel.url)
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error)
            .into(photo)

        // Configurar el clic en el botón "Añadir"
        btnAdd.setOnClickListener {
            onAddClick(favoritoModel) // Llamar a la función onAddClick con el ítem seleccionado
        }

        btnFav.setOnClickListener {
            onDeleteClick(favoritoModel)
        }
    }
}