package com.example.bloom.macetasaccesorios

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.bloom.base.ProductoBaseViewHolder
import com.example.bloom.R

class MacetaAccesorioViewHolder(view: View) : ProductoBaseViewHolder<MacetaAccesorio>(view) {
    override val name: TextView = view.findViewById(R.id.nombreMacetaAccesorio)
    override val photo: ImageView = view.findViewById(R.id.imgMacetaAccesorio)
    override val precio: TextView = view.findViewById(R.id.precioMacetaAccesorio)
    override val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    override val btnFav: ImageButton = view.findViewById(R.id.btnFav)
}
