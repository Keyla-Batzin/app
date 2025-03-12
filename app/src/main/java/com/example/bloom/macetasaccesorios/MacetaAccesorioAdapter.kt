package com.example.bloom.macetasaccesorios


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import com.example.bloom.plantasexterior.PlantaExterior

class MacetaAccesorioAdapter(
    val macetasAccesoriosList: List<MacetaAccesorio>,
    private val onAddClick: (MacetaAccesorio) -> Unit
) : RecyclerView.Adapter<MacetaAccesorioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MacetaAccesorioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MacetaAccesorioViewHolder(layoutInflater.inflate(R.layout.item_maceta_accesorio, parent, false))
    }

    override fun onBindViewHolder(holder: MacetaAccesorioViewHolder, position: Int) {
        val item = macetasAccesoriosList[position]
        holder.render(item, onAddClick)
    }

    override fun getItemCount(): Int = macetasAccesoriosList.size
}