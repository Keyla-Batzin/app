package com.example.bloom.floreseventos

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class FloresEventosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreFloresEventos)
    val photo: ImageView = view.findViewById(R.id.imgFloresEventos)
    val precio: TextView = view.findViewById(R.id.precioFloresEventos)

    fun render(floresEventosModel: FloresEventos) {
        name.text = floresEventosModel.nombre
        precio.text = floresEventosModel.precio

        Log.d("ImageURL", "Cargando imagen desde: ${floresEventosModel.url}")
        Glide.with(photo.context)
            .load(floresEventosModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}