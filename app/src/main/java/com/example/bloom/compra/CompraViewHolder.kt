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
    val cantidad: TextView = view.findViewById(R.id.cantidadCompra)
    val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    val btnSuma: ImageButton = view.findViewById(R.id.btnSuma)
    val btnResta: ImageButton = view.findViewById(R.id.btnResta)

    fun render(compra: Compra, onDeleteClick: (Compra) -> Unit, onSumaClick: (Compra) -> Unit, onRestaClick: (Compra) -> Unit) {
        name.text = compra.nombre
        "${compra.precio} €".also { precio.text = it }
        cantidad.text = "${compra.cantidad}"

        Glide.with(photo.context)
            .load(compra.url)
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error)
            .into(photo)

        btnDelete.setOnClickListener {
            onDeleteClick(compra)
        }

        btnSuma.setOnClickListener {
            onSumaClick(compra)  // Llama a la función para sumar cantidad
        }

        btnResta.setOnClickListener {
            onRestaClick(compra)  // Llama a la función para restar cantidad
        }
    }
}