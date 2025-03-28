package com.example.bloom.macetasaccesorios

import com.example.bloom.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bloom.base.ProductoBaseAdapter

class MacetaAccesorioAdapter(
    items: List<MacetaAccesorio>,
    onAddClick: (MacetaAccesorio) -> Unit,
    onAddFavClick: (MacetaAccesorio) -> Unit
) : ProductoBaseAdapter<MacetaAccesorio, MacetaAccesorioViewHolder>(items, onAddClick, onAddFavClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacetaAccesorioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MacetaAccesorioViewHolder(layoutInflater.inflate(R.layout.item_maceta_accesorio, parent, false))
    }
}
