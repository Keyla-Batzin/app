package com.example.bloom.pack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PacksFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: PackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pack, container, false)

        rv = view.findViewById(R.id.recyclerPack)
        rv.layoutManager = GridLayoutManager(requireContext(), 3) // 3 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = PackAdapter(emptyList())
        rv.adapter = adapter

        // Cargar datos
        actualizaPacks()

        return view
    }

    private fun actualizaPacks() {
        val service = PackAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaPacks = service.obtenerTodosPacks()

                // Verificar si la lista no es nula
                if (!listaPacks.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = PackAdapter(listaPacks)
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de packs vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }
}