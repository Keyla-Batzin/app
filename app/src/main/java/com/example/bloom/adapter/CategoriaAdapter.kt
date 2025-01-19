package com.example.bloom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.Categoria

class CategoriaAdapter(val categoriasList:List<Categoria>) : RecyclerView.Adapter<CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val layoutInflater = LayoutInflater.from()
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = categoriasList.size
}