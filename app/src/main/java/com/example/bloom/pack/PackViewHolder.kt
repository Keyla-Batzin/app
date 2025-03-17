package com.example.bloom.pack

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class PackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombrePack)
    val photo: ImageView = view.findViewById(R.id.imgPack)
    val precio: TextView = view.findViewById(R.id.precioPack)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    val btnFav: ImageButton = view.findViewById(R.id.btnFav)

    fun render(packModel: Pack, onAddClick: (Pack) -> Unit, onAddFavClick: (Pack) -> Unit) {
        name.text = packModel.nombre
        precio.text = buildString {
            append(packModel.precio.toString())
            append("€")
        }

        Log.d("ImageURL", "Cargando imagen desde: ${packModel.url}")
        Glide.with(photo.context)
            .load(packModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView

        btnAdd.setOnClickListener {
            onAddClick(packModel)
        }

        btnFav.setOnClickListener {
            onAddFavClick(packModel)
            btnFav.setImageResource(R.drawable.heart1_relleno)
        }
    }
}