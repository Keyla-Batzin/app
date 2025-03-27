package com.example.bloom.plantasinterior

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.bloom.R
import com.example.bloom.base.ProductoBaseViewHolder
import com.example.bloom.ramosflores.RamoFlor

class PlantaInteriorViewHolder(view: View) : ProductoBaseViewHolder<RamoFlor>(view) {
    override val name: TextView = view.findViewById(R.id.nombrePlantaInterior)
    override val photo: ImageView = view.findViewById(R.id.imgPlantaInterior)
    override val precio: TextView = view.findViewById(R.id.precioPlantaInterior)
    override val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    override val btnFav: ImageButton = view.findViewById(R.id.btnFav)
}