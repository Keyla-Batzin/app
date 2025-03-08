package com.example.bloom.categorias

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
import com.example.bloom.plantasinterior.PlantasInteriorFragment
import com.example.bloom.ramosflores.RamosFloresFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriasFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CategoriaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        rv = view.findViewById(R.id.recyclerCategorias)
        rv.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columnas

        // 🔹 Asignar un adaptador vacío antes de la carga de datos
        adapter = CategoriaAdapter(emptyList()) { categoria ->
            // Manejar clic en el ítem
            cambiarFragment(categoria.id)
        }
        rv.adapter = adapter

        // Cargar datos
        actualizaCategorias()

        return view
    }

    private fun actualizaCategorias() {
        val service = CategoriaAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaCategorias = service.obtenerTodasCategorias()

                // Verificar si la lista no es nula
                if (!listaCategorias.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        // Actualizar el adaptador con los nuevos datos
                        adapter = CategoriaAdapter(listaCategorias) { categoria ->
                            cambiarFragment(categoria.id)
                        }
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista de categorías vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }

    private fun cambiarFragment(categoriaId: Int) {
        val fragment = when (categoriaId) {
            1 -> RamosFloresFragment()
            2 -> PlantasInteriorFragment()
            //3 -> SuculentasFragment()
            //4 -> HerramientasFragment()
            //5 -> MacetasFragment()
            //6 -> DecoracionFragment()
            else -> throw IllegalArgumentException("Categoría no válida")
        }

        // Reemplazar el fragmento actual con el nuevo
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .addToBackStack(null) // Opcional: Agregar a la pila de retroceso
            .commit()
    }
}