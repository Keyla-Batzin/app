package com.example.bloom.plantasexterior

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.bloom.base.ProductoBaseViewHolder
import com.example.bloom.R

class PlantaExteriorViewHolder(view: View) : ProductoBaseViewHolder<PlantaExterior>(view) {
    override val name: TextView = view.findViewById(R.id.nombrePlantaExterior)
    override val photo: ImageView = view.findViewById(R.id.imgPlantaExterior)
    override val precio: TextView = view.findViewById(R.id.precioPlantaExterior)
    override val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    override val btnFav: ImageButton = view.findViewById(R.id.btnFav)
}
