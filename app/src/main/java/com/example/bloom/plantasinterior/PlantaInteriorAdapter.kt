package com.example.bloom.plantasinterior

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.ramosflores.RamoFlor

class PlantaInteriorAdapter(
    val plantasInteriorList: List<PlantaInterior>,
    private val onAddClick: (PlantaInterior) -> Unit,
    private val onAddFavClick: (PlantaInterior) -> Unit
)    : RecyclerView.Adapter<PlantaInteriorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaInteriorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlantaInteriorViewHolder(layoutInflater.inflate(R.layout.item_planta_interior, parent, false))
    }

    override fun onBindViewHolder(holder: PlantaInteriorViewHolder, position: Int) {
        val item = plantasInteriorList[position]
        holder.render(item, onAddClick, onAddFavClick)
    }

    override fun getItemCount(): Int = plantasInteriorList.size
}