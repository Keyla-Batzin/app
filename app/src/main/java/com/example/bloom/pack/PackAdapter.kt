package com.example.bloom.pack

import com.example.bloom.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bloom.base.ProductoBaseAdapter

class PackAdapter(
    items: List<Pack>,
    onAddClick: (Pack) -> Unit,
    onAddFavClick: (Pack) -> Unit
) : ProductoBaseAdapter<Pack, PackViewHolder>(items, onAddClick, onAddFavClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PackViewHolder(layoutInflater.inflate(R.layout.item_pack, parent, false))
    }
}