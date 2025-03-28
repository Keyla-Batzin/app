package com.example.bloom.plantasexterior

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bloom.R
import com.example.bloom.base.ProductoBaseAdapter

class PlantaExteriorAdapter(
    items: List<PlantaExterior>,
    onAddClick: (PlantaExterior) -> Unit,
    onAddFavClick: (PlantaExterior) -> Unit
) : ProductoBaseAdapter<PlantaExterior, PlantaExteriorViewHolder>(items, onAddClick, onAddFavClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaExteriorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlantaExteriorViewHolder(layoutInflater.inflate(R.layout.item_planta_exterior, parent, false))
    }
}
