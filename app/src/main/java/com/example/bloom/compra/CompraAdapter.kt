package com.example.bloom.compra

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class CompraAdapter(
    var comprasList: MutableList<Compra>,
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

    fun removeItem(position: Int) {
        comprasList.removeAt(position)
        notifyItemRemoved(position)
        if (comprasList.isEmpty()) {
            // Aquí puedes mostrar un mensaje o un estado vacío
            Log.d("Adapter", "No hay más ítems en la lista")
        }
    }

    fun updateList(newList: List<Compra>) {
        comprasList.clear()          // Limpia la lista actual
        comprasList.addAll(newList) // Agrega los nuevos elementos
        notifyDataSetChanged()      // Notifica al RecyclerView que los datos han cambiado
    }
}