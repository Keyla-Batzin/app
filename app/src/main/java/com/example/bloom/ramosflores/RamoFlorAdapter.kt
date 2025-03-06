package com.example.bloom.ramosflores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class RamoFlorAdapter(
    val ramos_floresList: List<RamoFlor>,
    private val onAddClick: (RamoFlor) -> Unit // Función para manejar el clic en "Añadir"
) : RecyclerView.Adapter<RamoFlorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RamoFlorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RamoFlorViewHolder(layoutInflater.inflate(R.layout.item_ramo_flores, parent, false))
    }

    override fun onBindViewHolder(holder: RamoFlorViewHolder, position: Int) {
        val item = ramos_floresList[position]
        holder.render(item, onAddClick) // Pasar la función onAddClick al ViewHolder
    }

    override fun getItemCount(): Int = ramos_floresList.size
}