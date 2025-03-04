package com.example.bloom.plantasexterior

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class PlantaExteriorAdapter(val plantasExteriorList: List<PlantaExterior>) : RecyclerView.Adapter<PlantaExteriorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaExteriorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlantaExteriorViewHolder(layoutInflater.inflate(R.layout.item_planta_exterior, parent, false))
    }

    override fun onBindViewHolder(holder: PlantaExteriorViewHolder, position: Int) {
        val item = plantasExteriorList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = plantasExteriorList.size
}