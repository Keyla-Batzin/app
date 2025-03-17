package com.example.bloom.macetasaccesorios

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R
import com.example.bloom.plantasexterior.PlantaExterior
import com.example.bloom.ramosflores.RamoFlor

class MacetaAccesorioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreMacetaAccesorio)
    val photo: ImageView = view.findViewById(R.id.imgMacetaAccesorio)
    val precio: TextView = view.findViewById(R.id.precioMacetaAccesorio)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    val btnFav: ImageButton = view.findViewById(R.id.btnFav)

    fun render(macetaAccesorioModel: MacetaAccesorio, onAddClick: (MacetaAccesorio) -> Unit, onAddFavClick: (MacetaAccesorio) -> Unit) {
        name.text = macetaAccesorioModel.nombre
        precio.text = buildString {
            append(macetaAccesorioModel.precio.toString())
            append("€")
        }

        Log.d("ImageURL", "Cargando imagen desde: ${macetaAccesorioModel.url}")
        Glide.with(photo.context)
            .load(macetaAccesorioModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView

        btnAdd.setOnClickListener {
            onAddClick(macetaAccesorioModel)
            btnAdd.setImageResource(R.drawable.shop_check)
        }

        btnFav.setOnClickListener {
            onAddFavClick(macetaAccesorioModel)
            btnFav.setImageResource(R.drawable.heart1_relleno)
        }
    }
}