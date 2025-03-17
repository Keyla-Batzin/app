package com.example.bloom.productos

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R
import com.example.bloom.ramosflores.RamoFlor

class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreProducto)
    val photo: ImageView = view.findViewById(R.id.imgProducto)
    val precio: TextView = view.findViewById(R.id.precioProducto)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    val btnFav: ImageButton = view.findViewById(R.id.btnFav)

    fun render(productoModel: Producto, onAddClick: (Producto) -> Unit, onAddFavClick: (Producto) -> Unit) {
        name.text = productoModel.nombre
        precio.text = buildString {
            append(productoModel.precio.toString())
            append("€")
        }

        Log.d("ImageURL", "Cargando imagen desde: ${productoModel.url}")
        Glide.with(photo.context)
            .load(productoModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView

        btnAdd.setOnClickListener {
            onAddClick(productoModel) // Llamar a la función onAddClick con el ítem seleccionado
        }

        btnFav.setOnClickListener {
            onAddFavClick(productoModel)
            btnFav.setImageResource(R.drawable.heart1_relleno)
        }
    }
}