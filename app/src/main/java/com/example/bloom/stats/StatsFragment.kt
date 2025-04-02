package com.example.bloom.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bloom.R
import com.example.bloom.databinding.FragmentStatsBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var viewModel: SharedViewModel
    private val PREFS_FILENAME = "com.example.bloom.prefs"
    private val LOGIN_COUNT = "login_count"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        viewModel.categoriaStats.observe(viewLifecycleOwner) { categoriaStats ->
            categoriaStats?.let { updatePieChart(it) }
        }

        // Mostrar el contador de inicios de sesión correctamente
        val prefs = requireActivity().getSharedPreferences(PREFS_FILENAME, AppCompatActivity.MODE_PRIVATE)
        val loginCount = prefs.getInt(LOGIN_COUNT, 0)
        binding.txtInicioSesion.text = "$loginCount"
    }

    // Garfico circular Categorias
    private fun updatePieChart(categoriaStats: List<CategoriaStats>) {
        val entries = categoriaStats
            .filter { it.clickCount != 0 } // Filtrar categorías sin clicks
            .map { stat -> PieEntry(stat.clickCount.toFloat(), stat.categoriaName) }

        if (entries.isEmpty()) {
            binding.grafCategorias.clear() // Limpia el gráfico si no hay datos
            return
        }

        val pieDataSet = PieDataSet(entries, "Clicks por Categoría")

        val colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.ramos),
            ContextCompat.getColor(requireContext(), R.color.plantaInt),
            ContextCompat.getColor(requireContext(), R.color.plantExt),
            ContextCompat.getColor(requireContext(), R.color.floresEv),
            ContextCompat.getColor(requireContext(), R.color.macetas),
            ContextCompat.getColor(requireContext(), R.color.pack)
        ).take(entries.size) // Asegurar que la cantidad de colores coincida con las categorías

        pieDataSet.colors = colors
        pieDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        pieDataSet.valueTextSize = 16f

        binding.grafCategorias.apply {
            data = PieData(pieDataSet)
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(ContextCompat.getColor(requireContext(), R.color.white))
            holeRadius = 40f
            transparentCircleRadius = 45f
            setTransparentCircleColor(ContextCompat.getColor(requireContext(), R.color.grey))
            setTransparentCircleAlpha(110)
            animateY(1000)
            invalidate()
        }
    }

}


