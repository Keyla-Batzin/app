package com.example.bloom.compra

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class PrecioTotalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val precioTotal: TextView = view.findViewById(R.id.precioTotal)

    fun render(compra: PrecioTotalResponse) {
        "${compra.precio_total} €".also { precioTotal.text = it }  // Muestra el precio como Float
    }
}