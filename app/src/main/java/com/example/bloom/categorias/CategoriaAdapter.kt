package com.example.bloom.categorias

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class CategoriaAdapter(private val categoriasList:List<Categoria>) : RecyclerView.Adapter<CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoriaViewHolder(layoutInflater.inflate(R.layout.item_categoria, parent, false))
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val item = categoriasList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = categoriasList.size
}