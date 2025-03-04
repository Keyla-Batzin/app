package com.example.bloom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.floreseventos.FloresEventosAPI
import com.example.bloom.floreseventos.FloresEventosAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FloresEventosFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: FloresEventosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flores_eventos, container, false)

        rv = view.findViewById(R.id.recyclerFloresEventos)
        rv.layoutManager = GridLayoutManager(requireContext(), 3) // 3 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = FloresEventosAdapter(emptyList())
        rv.adapter = adapter

        // Cargar datos
        actualizaFloresEventos()

        return view
    }

    private fun actualizaFloresEventos() {
        val service = FloresEventosAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaFlores = service.obtenerTodasFloresEventos()

                // Verificar si la lista no es nula
                if (!listaFlores.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = FloresEventosAdapter(listaFlores)
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de flores para eventos vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }
}