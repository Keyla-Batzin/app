package com.example.bloom.pack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.ramosflores.RamoFlor

class PackAdapter(
    val packsList: List<Pack>,
    private val onAddClick: (Pack) -> Unit,
    private val onAddFavClick: (Pack) -> Unit
) : RecyclerView.Adapter<PackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PackViewHolder(layoutInflater.inflate(R.layout.item_pack, parent, false))
    }

    override fun onBindViewHolder(holder: PackViewHolder, position: Int) {
        val item = packsList[position]
        holder.render(item, onAddClick, onAddFavClick)
    }

    override fun getItemCount(): Int = packsList.size
}