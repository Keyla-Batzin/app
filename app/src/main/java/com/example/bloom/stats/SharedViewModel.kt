package com.example.bloom.stats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {
    val categoriaStats = MutableLiveData<List<CategoriaStats>>()
    val compraStats = MutableLiveData<List<CompraStats>>()
}
