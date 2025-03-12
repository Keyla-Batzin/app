package com.example.bloom.floreseventos

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.FloresEventosFragment
import com.example.bloom.R
import com.example.bloom.plantasexterior.PlantaExterior

class FloresEventosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreFloresEventos)
    val photo: ImageView = view.findViewById(R.id.imgFloresEventos)
    val precio: TextView = view.findViewById(R.id.precioFloresEventos)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)

    fun render(floresEventosModel: FloresEventos, onAddClick: (FloresEventos) -> Unit) {
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
        }
    }
}