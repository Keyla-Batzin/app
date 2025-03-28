package com.example.bloom.pack

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.bloom.base.ProductoBaseViewHolder
import com.example.bloom.R

class PackViewHolder(view: View) : ProductoBaseViewHolder<Pack>(view) {
    override val name: TextView = view.findViewById(R.id.nombrePack)
    override val photo: ImageView = view.findViewById(R.id.imgPack)
    override val precio: TextView = view.findViewById(R.id.precioPack)
    override val btnAdd: ImageButton = view.findViewById(R.id.btnAdd)
    override val btnFav: ImageButton = view.findViewById(R.id.btnFav)
}
