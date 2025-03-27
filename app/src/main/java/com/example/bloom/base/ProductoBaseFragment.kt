package com.example.bloom.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ProductoBaseFragment<T : ProductoBase, A : ProductoBaseAdapter<T, *>> : Fragment() {
    protected lateinit var rv: RecyclerView
    protected lateinit var adapter: A

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutResource(), container, false)
        rv = view.findViewById(getRecyclerViewId())
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = createAdapter(emptyList())
        rv.adapter = adapter
        loadData()
        return view
    }

    protected abstract fun getLayoutResource(): Int
    protected abstract fun getRecyclerViewId(): Int
    protected abstract fun createAdapter(items: List<T>): A
    protected abstract suspend fun fetchData(): List<T>
    protected abstract fun addToCart(item: T)
    protected abstract fun addToFavorites(item: T)

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val items = fetchData()
                if (!items.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        adapter = createAdapter(items)
                        rv.adapter = adapter
                    }
                } else {
                    Log.e("API", "Lista vacía o nula")
                }
            } catch (e: Exception) {
                Log.e("API", "Error al obtener datos", e)
            }
        }
    }
}
