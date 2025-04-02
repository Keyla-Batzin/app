package com.example.bloom.categorias

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.stats.SharedViewModel
import com.example.bloom.stats.CategoriaStats


class CategoriaAdapter(
    private val categoriasList: List<Categoria>,
    private val categoriaStatsList: MutableList<CategoriaStats>,
    private val onItemClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoriaViewHolder(layoutInflater.inflate(R.layout.item_categoria, parent, false))
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val item = categoriasList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
            // Incrementa el contador de clics
            categoriaStatsList[position].clickCount++
            // Notifica al ViewModel sobre el cambio
            (holder.itemView.context as? AppCompatActivity)?.let { activity ->
                ViewModelProvider(activity).get(SharedViewModel::class.java).categoriaStats.value = categoriaStatsList
            }
        }
    }

    override fun getItemCount(): Int = categoriasList.size
}
