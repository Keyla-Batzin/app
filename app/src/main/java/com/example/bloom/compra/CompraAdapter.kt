package com.example.bloom.compra

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class CompraAdapter(
    var comprasList: MutableList<Compra>,
    private val onDeleteClick: (Compra) -> Unit, // Función para manejar el clic en "Eliminar"
    private val onSumaClick: (Compra) -> Unit,  // Función para manejar el clic en "Sumar"
    private val onRestaClick: (Compra) -> Unit  // Función para manejar el clic en "Restar"
) : RecyclerView.Adapter<CompraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CompraViewHolder(layoutInflater.inflate(R.layout.item_compra, parent, false))
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val item = comprasList[position]
        holder.render(item, onDeleteClick, onSumaClick, onRestaClick)  // Pasar las funciones al ViewHolder
    }

    override fun getItemCount(): Int = comprasList.size

    fun removeItem(position: Int) {
        comprasList.removeAt(position)
        notifyItemRemoved(position)
        if (comprasList.isEmpty()) {
            Log.d("Adapter", "No hay más ítems en la lista")
        }
    }

    fun updateList(newList: List<Compra>) {
        comprasList.clear()
        comprasList.addAll(newList)
        notifyDataSetChanged()
    }
}