package com.example.bloom.floreseventos

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class FloresEventosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreFloresEventos)
    val photo: ImageView = view.findViewById(R.id.imgFloresEventos)
    val precio: TextView = view.findViewById(R.id.precioFloresEventos)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    val btnFav: ImageButton = view.findViewById(R.id.btnFav)

    fun render(floresEventosModel: FloresEventos, onAddClick: (FloresEventos) -> Unit, onAddFavClick: (FloresEventos) -> Unit) {
        name.text = floresEventosModel.nombre
        precio.text = buildString {
            append(floresEventosModel.precio.toString())
            append("€")
        }

        Log.d("ImageURL", "Cargando imagen desde: ${floresEventosModel.url}")
        Glide.with(photo.context)
            .load(floresEventosModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView

        btnAdd.setOnClickListener {
            onAddClick(floresEventosModel)
            btnAdd.setImageResource(R.drawable.shop_check)
        }

        btnFav.setOnClickListener {
            onAddFavClick(floresEventosModel)
            btnFav.setImageResource(R.drawable.heart1_relleno)
        }

    }
}