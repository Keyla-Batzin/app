package com.example.bloom.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.ramosflores.RamoFlor

class ProductoAdapter(
    val productosList: List<Producto>,
    private val onAddClick: (Producto) -> Unit,
    private val onAddFavClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.item_producto, parent, false))
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item = productosList[position]
        holder.render(item,onAddClick, onAddFavClick)
    }

    override fun getItemCount(): Int = productosList.size
}