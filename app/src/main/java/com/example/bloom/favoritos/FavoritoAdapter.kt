package com.example.bloom.favoritos

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.compra.Compra

class FavoritoAdapter(
    val favoritoList: MutableList<Favorito>,
    private val onAddClick: (Favorito) -> Unit, // Función para manejar el clic en "Añadir"
    private val onDeleteClick: (Favorito) -> Unit
) : RecyclerView.Adapter<FavoritoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritoViewHolder(layoutInflater.inflate(R.layout.item_favorito, parent, false))
    }

    override fun onBindViewHolder(holder: FavoritoViewHolder, position: Int) {
        val item = favoritoList[position]
        holder.render(item, onAddClick, onDeleteClick) // Pasar la función onAddClick al ViewHolder
    }

    override fun getItemCount(): Int = favoritoList.size

    fun removeItem(position: Int) {
        favoritoList.removeAt(position)
        notifyItemRemoved(position)
        if (favoritoList.isEmpty()) {
            Log.d("Adapter", "No hay más ítems en la lista")
        }
    }

    fun updateList(newList: List<Favorito>) {
        favoritoList.clear()
        favoritoList.addAll(newList)
        notifyDataSetChanged() // Asegura que el RecyclerView se actualiza
    }

}