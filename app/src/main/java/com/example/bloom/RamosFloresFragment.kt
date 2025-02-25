package com.example.bloom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.ramosflores.RamoFlorAdapter
import com.example.recyclerview.reserves.RamoFlorAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RamosFloresFragment : Fragment() {

    private lateinit var rv:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_ramos_flores, container, false)

        // Inicializar vistas
        rv = view.findViewById(R.id.recyclerRamosFlores)

        // Configurar RecyclerView
        rv.layoutManager = GridLayoutManager(requireContext(), 3) // 3 columnas

        // Cargar datos
        actualitzaRamosFlores()

        return view
    }

    private fun actualitzaRamosFlores() {
        var service= RamoFlorAPI.API()
        lifecycleScope.launch(Dispatchers.IO) {
            val llistaRamos=service.obtenerTodosRamoFlor()
            withContext(Dispatchers.Main) {
                var reservaadapter=RamoFlorAdapter(llistaRamos)
                rv.adapter=reservaadapter
            }
        }
    }
}