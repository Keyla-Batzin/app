package com.example.bloom.ramosflores

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.bloom.base.ProductoBaseViewHolder
import com.example.bloom.R

class RamoFlorViewHolder(view: View) : ProductoBaseViewHolder<RamoFlor>(view) {
    override val name: TextView = view.findViewById(R.id.nombreRamosFlores)
    override val photo: ImageView = view.findViewById(R.id.imgRamosFlores)
    override val precio: TextView = view.findViewById(R.id.precioRamosFlores)
    override val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    override val btnFav: ImageButton = view.findViewById(R.id.btnFav)
}
