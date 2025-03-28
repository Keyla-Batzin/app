package com.example.bloom.floreseventos

import com.example.bloom.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bloom.base.ProductoBaseAdapter

class FlorEventoAdapter(
    items: List<FlorEvento>,
    onAddClick: (FlorEvento) -> Unit,
    onAddFavClick: (FlorEvento) -> Unit
) : ProductoBaseAdapter<FlorEvento, FlorEventoViewHolder>(items, onAddClick, onAddFavClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlorEventoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FlorEventoViewHolder(layoutInflater.inflate(R.layout.item_flor_evento, parent, false))
    }
}