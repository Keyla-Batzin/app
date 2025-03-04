package com.example.bloom.floreseventos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class FloresEventosAdapter(val floresEventosList: List<FloresEventos>) : RecyclerView.Adapter<FloresEventosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloresEventosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FloresEventosViewHolder(layoutInflater.inflate(R.layout.item_flor_evento, parent, false))
    }

    override fun onBindViewHolder(holder: FloresEventosViewHolder, position: Int) {
        val item = floresEventosList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = floresEventosList.size
}