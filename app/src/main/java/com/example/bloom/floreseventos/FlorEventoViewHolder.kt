package com.example.bloom.floreseventos

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.bloom.base.ProductoBaseViewHolder
import com.example.bloom.R

class FlorEventoViewHolder(view: View) : ProductoBaseViewHolder<FlorEvento>(view) {
    override val name: TextView = view.findViewById(R.id.nombreFloresEventos)
    override val photo: ImageView = view.findViewById(R.id.imgFloresEventos)
    override val precio: TextView = view.findViewById(R.id.precioFloresEventos)
    override val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    override val btnFav: ImageButton = view.findViewById(R.id.btnFav)
}
