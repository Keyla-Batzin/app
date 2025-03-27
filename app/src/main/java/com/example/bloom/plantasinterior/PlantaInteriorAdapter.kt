package com.example.bloom.plantasinterior

import com.example.bloom.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bloom.base.ProductoBaseAdapter

class PlantaInteriorAdapter(
    items: List<PlantaInterior>,
    onAddClick: (PlantaInterior) -> Unit,
    onAddFavClick: (PlantaInterior) -> Unit
) : ProductoBaseAdapter<PlantaInterior, PlantaInteriorViewHolder>(items, onAddClick, onAddFavClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaInteriorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlantaInteriorViewHolder(layoutInflater.inflate(R.layout.item_planta_interior, parent, false))
    }
}