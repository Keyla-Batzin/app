package com.example.bloom.ramosflores

import com.example.bloom.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bloom.base.ProductoBaseAdapter

class RamoFlorAdapter(
    items: List<RamoFlor>,
    onAddClick: (RamoFlor) -> Unit,
    onAddFavClick: (RamoFlor) -> Unit
) : ProductoBaseAdapter<RamoFlor, RamoFlorViewHolder>(items, onAddClick, onAddFavClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RamoFlorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RamoFlorViewHolder(layoutInflater.inflate(R.layout.item_ramo_flores, parent, false))
    }
}

