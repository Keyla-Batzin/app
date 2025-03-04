package com.example.bloom.productos

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreProducto)
    val photo: ImageView = view.findViewById(R.id.imgProducto)
    val precio: TextView = view.findViewById(R.id.precioProducto)

    fun render(productoModel: Producto) {
        name.text = productoModel.nombre
        precio.text = productoModel.precio

        Log.d("ImageURL", "Cargando imagen desde: ${productoModel.url}")
        Glide.with(photo.context)
            .load(productoModel.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView
    }
}