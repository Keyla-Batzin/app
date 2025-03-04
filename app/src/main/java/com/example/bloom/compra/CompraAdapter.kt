package com.example.bloom.compra

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class CompraAdapter(
    val comprasList: List<Compra>,
    private val onDeleteClick: (Compra) -> Unit  // Función para manejar el clic en "Eliminar"
) : RecyclerView.Adapter<CompraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CompraViewHolder(layoutInflater.inflate(R.layout.item_compra, parent, false))
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val item = comprasList[position]
        holder.render(item, onDeleteClick)  // Pasar la función onDeleteClick al ViewHolder
    }

    override fun getItemCount(): Int = comprasList.size
}