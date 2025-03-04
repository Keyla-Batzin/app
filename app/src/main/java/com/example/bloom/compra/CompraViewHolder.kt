package com.example.bloom.compra

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class CompraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreCompra)
    val photo: ImageView = view.findViewById(R.id.imgCompra)
    val precio: TextView = view.findViewById(R.id.precioCompra)
    val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)  // Referencia al botón de eliminar

    fun render(compra: Compra, onDeleteClick: (Compra) -> Unit) {
        name.text = compra.nombre
        precio.text = compra.precio

        Log.d("ImageURL", "Cargando imagen desde: ${compra.url}")
        Glide.with(photo.context)
            .load(compra.url) // URL de la imagen
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error) // Imagen si hay error
            .into(photo) // Cargamos la imagen en el ImageView

        // Configurar el clic en el botón de eliminar
        btnDelete.setOnClickListener {
            onDeleteClick(compra)  // Llamar a la función onDeleteClick con la compra seleccionada
        }
    }
}