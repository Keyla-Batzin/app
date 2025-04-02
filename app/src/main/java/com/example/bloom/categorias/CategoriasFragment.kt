package com.example.bloom.categorias

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloom.floreseventos.FloresEventosFragment
import com.example.bloom.R
import com.example.bloom.stats.SharedViewModel
import com.example.bloom.macetasaccesorios.MacetasAccesoriosFragment
import com.example.bloom.pack.PackFragment
import com.example.bloom.stats.CategoriaStats
import com.example.bloom.plantasexterior.PlantasExteriorFragment
import com.example.bloom.plantasinterior.PlantasInteriorFragment
import com.example.bloom.ramosflores.RamosFloresFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoriasFragment : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CategoriaAdapter
    private lateinit var viewModel: SharedViewModel
    private val categoriaStatsList = mutableListOf<CategoriaStats>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        rv = view.findViewById(R.id.recyclerCategorias)
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = CategoriaAdapter(emptyList(), categoriaStatsList) { categoria ->
            cambiarFragment(categoria.id)
        }
        rv.adapter = adapter

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Cargar datos
        actualizaCategorias()

        return view
    }

    private fun actualizaCategorias() {
        val service = CategoriaAPI.API()
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listaCategorias = service.obtenerTodasCategorias()
                if (!listaCategorias.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        if (categoriaStatsList.isEmpty()) {
                            listaCategorias.forEach { categoria ->
                                categoriaStatsList.add(CategoriaStats(categoria.id, categoriaName = categoria.nombre))
                            }
                        }
                        adapter = CategoriaAdapter(listaCategorias, categoriaStatsList) { categoria ->
                            cambiarFragment(categoria.id)
                        }
                        rv.adapter = adapter
                        // Actualiza el ViewModel con las estadísticas
                        viewModel.categoriaStats.value = categoriaStatsList
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
            3 -> PlantasExteriorFragment()
            4 -> FloresEventosFragment()
            5 -> MacetasAccesoriosFragment()
            6 -> PackFragment()
            else -> throw IllegalArgumentException("Categoría no válida")
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}
