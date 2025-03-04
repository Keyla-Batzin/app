package com.example.bloom.plantasinterior

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class PlantaInteriorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombrePlantaInterior)
    val photo: ImageView = view.findViewById(R.id.imgPlantaInterior)
    val precio: TextView = view.findViewById(R.id.precioPlantaInterior)

    fun render(plantaInteriorModel: PlantaInterior) {
        name.text = plantaInteriorModel.nombre
        precio.text = plantaInteriorModel.precio

        Log.d("ImageURL", "Cargando imagen desde: ${plantaInteriorModel.url}")
        Glide.with(photo.context)
            .load(plantaInteriorModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}