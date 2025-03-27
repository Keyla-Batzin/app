package com.example.bloom.base

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

abstract class ProductoBaseViewHolder<T : ProductoBase>(view: View) : RecyclerView.ViewHolder(view) {
    abstract val name: TextView
    abstract val photo: ImageView
    abstract val precio: TextView
    abstract val btnAdd: ImageButton
    abstract val btnFav: ImageButton

    fun render(item: T, onAddClick: (T) -> Unit, onAddFavClick: (T) -> Unit) {
        name.text = item.nombre
        precio.text = "${item.precio}€"

        Glide.with(photo.context)
            .load(item.url)
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error)
            .into(photo)

        btnAdd.setOnClickListener {
            onAddClick(item)
            btnAdd.setImageResource(R.drawable.shop_check)
        }

        btnFav.setOnClickListener {
            onAddFavClick(item)
            btnFav.setImageResource(R.drawable.heart1_relleno)
        }
    }
}
