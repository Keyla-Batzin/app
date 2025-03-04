package com.example.bloom.macetasaccesorios

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class MacetaAccesorioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreMacetaAccesorio)
    val photo: ImageView = view.findViewById(R.id.imgMacetaAccesorio)
    val precio: TextView = view.findViewById(R.id.precioMacetaAccesorio)

    fun render(macetaAccesorioModel: MacetaAccesorio) {
        name.text = macetaAccesorioModel.nombre
        precio.text = macetaAccesorioModel.precio

        Log.d("ImageURL", "Cargando imagen desde: ${macetaAccesorioModel.url}")
        Glide.with(photo.context)
            .load(macetaAccesorioModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}