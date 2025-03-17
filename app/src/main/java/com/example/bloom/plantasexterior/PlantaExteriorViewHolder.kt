package com.example.bloom.plantasexterior

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R
import com.example.bloom.ramosflores.RamoFlor

class PlantaExteriorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombrePlantaExterior)
    val photo: ImageView = view.findViewById(R.id.imgPlantaExterior)
    val precio: TextView = view.findViewById(R.id.precioPlantaExterior)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    val btnFav: ImageButton = view.findViewById(R.id.btnFav)

    fun render(plantaExteriorModel: PlantaExterior, onAddClick: (PlantaExterior) -> Unit, onAddFavClick: (PlantaExterior) -> Unit) {
        name.text = plantaExteriorModel.nombre
        precio.text = buildString {
            append(plantaExteriorModel.precio.toString())
            append("€")
        }

        Log.d("ImageURL", "Cargando imagen desde: ${plantaExteriorModel.url}")
        Glide.with(photo.context)
            .load(plantaExteriorModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView

        // Configurar el clic en el botón "Añadir"
        btnAdd.setOnClickListener {
            onAddClick(plantaExteriorModel)
        }

        btnFav.setOnClickListener {
            onAddFavClick(plantaExteriorModel)
            btnFav.setImageResource(R.drawable.heart1_relleno)
        }
    }
}