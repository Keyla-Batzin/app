package com.example.bloom.plantasexterior

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class PlantaExteriorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombrePlantaExterior)
    val photo: ImageView = view.findViewById(R.id.imgPlantaExterior)
    val precio: TextView = view.findViewById(R.id.precioPlantaExterior)

    fun render(plantaExteriorModel: PlantaExterior) {
        name.text = plantaExteriorModel.nombre
        precio.text = plantaExteriorModel.precio

        Log.d("ImageURL", "Cargando imagen desde: ${plantaExteriorModel.url}")
        Glide.with(photo.context)
            .load(plantaExteriorModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}