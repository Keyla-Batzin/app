package com.example.bloom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bloom.categorias.CategoriaStats
import com.example.bloom.compra.CompraStats

class SharedViewModel : ViewModel() {
    val categoriaStats = MutableLiveData<List<CategoriaStats>>()
    val compraStats = MutableLiveData<List<CompraStats>>()
    val precioTotal = MutableLiveData<Float>()
}
