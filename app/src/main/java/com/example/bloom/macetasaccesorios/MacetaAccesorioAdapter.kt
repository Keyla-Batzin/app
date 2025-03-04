package com.example.bloom.macetasaccesorios


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R

class MacetaAccesorioAdapter(val macetasAccesoriosList: List<MacetaAccesorio>) : RecyclerView.Adapter<MacetaAccesorioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacetaAccesorioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MacetaAccesorioViewHolder(layoutInflater.inflate(R.layout.item_maceta_accesorio, parent, false))
    }

    override fun onBindViewHolder(holder: MacetaAccesorioViewHolder, position: Int) {
        val item = macetasAccesoriosList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = macetasAccesoriosList.size
}