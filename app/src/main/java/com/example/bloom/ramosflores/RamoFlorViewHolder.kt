package com.example.bloom.ramosflores

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class RamoFlorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreRamosFlores)
    val photo: ImageView = view.findViewById(R.id.imgRamosFlores) // Cambio a ImageView
    val precio: TextView = view.findViewById(R.id.precioRamosFlores)

    fun render(ramos_floresModel: RamoFlor) {
        name.text = ramos_floresModel.nombre
        precio.text = ramos_floresModel.precio

        Log.d("ImageURL", "Cargando imagen desde: ${ramos_floresModel.imagen_url}")
        Glide.with(photo.context)
            .load(ramos_floresModel.imagen_url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.ramo_home) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}
