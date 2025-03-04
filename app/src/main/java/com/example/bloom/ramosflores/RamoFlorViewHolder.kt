package com.example.bloom.ramosflores

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloom.R

class RamoFlorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.nombreRamosFlores)
    val photo: ImageView = view.findViewById(R.id.imgRamosFlores)
    val precio: TextView = view.findViewById(R.id.precioRamosFlores)
    val btnAdd: ImageButton = view.findViewById(R.id.btnAdd) // Referencia al botón "Añadir"

    fun render(ramosFloresModel: RamoFlor, onAddClick: (RamoFlor) -> Unit) {
        name.text = ramosFloresModel.nombre
        precio.text = ramosFloresModel.precio.toString() // Asegúrate de que el precio sea un String

        Log.d("ImageURL", "Cargando imagen desde: ${ramosFloresModel.url}")
        Glide.with(photo.context)
            .load(ramosFloresModel.url)
            .placeholder(R.drawable.logo_peque)
            .error(R.drawable.img_error)
            .into(photo)

        // Configurar el clic en el botón "Añadir"
        btnAdd.setOnClickListener {
            onAddClick(ramosFloresModel) // Llamar a la función onAddClick con el ítem seleccionado
        }
    }
}
