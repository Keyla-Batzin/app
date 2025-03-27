package com.example.bloom.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ProductoBaseAdapter<T : ProductoBase, VH : RecyclerView.ViewHolder>(
    val items: List<T>,
    private val onAddClick: (T) -> Unit,
    private val onAddFavClick: (T) -> Unit
) : RecyclerView.Adapter<VH>() {

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        (holder as ProductoBaseViewHolder<T>).render(item, onAddClick, onAddFavClick)
    }

    override fun getItemCount(): Int = items.size
}
