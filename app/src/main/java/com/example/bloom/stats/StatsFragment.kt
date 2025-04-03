package com.example.bloom.stats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bloom.Mensaje
import com.example.bloom.R
import com.example.bloom.databinding.FragmentStatsBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var viewModel: SharedViewModel
    private lateinit var stats: Stats
    private lateinit var btnReset: Button
    private lateinit var db: FirebaseFirestore

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
        stats = requireActivity().application as Stats

        // Obtener y mostrar el contador de inicios de sesión
        stats.getLoginCount { loginCount ->
            binding.txtInicioSesion.text = loginCount.toString()
        }

        // Obtener estadísticas de categorías
        stats.getCategoriaStats { categoriaStats ->
            viewModel.categoriaStats.postValue(categoriaStats)
        }

        // Obtener estadísticas de compras
        stats.getCompraStats { compraStats ->
            viewModel.compraStats.postValue(compraStats)
        }

        // Observar cambios en las estadísticas de categorías y actualizar el gráfico
        viewModel.categoriaStats.observe(viewLifecycleOwner) { categoriaStats ->
            updatePieChart(categoriaStats)
        }

        // Observar cambios en las estadísticas de compras y actualizar el gráfico
        viewModel.compraStats.observe(viewLifecycleOwner) { compraStats ->
            updateBarChart(compraStats)
        }

        // Obtener referencia al botón "Resetear Estadísticas"
        btnReset = binding.root.findViewById(R.id.btnReset)

        // Establecer el OnClickListener para resetear las estadísticas
        btnReset.setOnClickListener {
            resetearEstadisticas()
        }
    }

    // Función para resetear las estadísticas de Firestore
    private fun resetearEstadisticas() {
        val db = FirebaseFirestore.getInstance()

        // Referencia a los tres documentos
        val categoriaStatsRef = db.collection("stats").document("categoriaStats")
        val compraStatsRef = db.collection("stats").document("compraStats")
        val loginCountRef = db.collection("stats").document("loginCount")

        // Borrar los campos correspondientes en cada documento
        categoriaStatsRef.update("categoriaId", FieldValue.delete(), "categoriaName", FieldValue.delete(), "clickCount", FieldValue.delete())
            .addOnSuccessListener {
                Log.d("StatsFragment", "Estadísticas de categorías reseteadas correctamente.")
            }
            .addOnFailureListener { e ->
                Log.w("StatsFragment", "Error reseteando estadísticas de categorías", e)
            }

        compraStatsRef.update("id", FieldValue.delete(), "precioTotal", FieldValue.delete())
            .addOnSuccessListener {
                Log.d("StatsFragment", "Estadísticas de compras reseteadas correctamente.")
            }
            .addOnFailureListener { e ->
                Log.w("StatsFragment", "Error reseteando estadísticas de compras", e)
            }

        loginCountRef.update("numLogin", FieldValue.delete())
            .addOnSuccessListener {
                Log.d("StatsFragment", "Estadísticas de login reseteadas correctamente.")
                // Mostrar mensaje de éxito
                Mensaje.mostrarPersonalizado(
                    view,
                    mensaje = "🔄️ Estadísticas reseteadas",
                    colorRes = R.color.morado_claro
                )

                // Limpiar gráficos y UI
                binding.grafCompra.clear()
                binding.grafCategorias.clear()
            }
            .addOnFailureListener { e ->
                Log.w("StatsFragment", "Error reseteando estadísticas de login", e)
                Toast.makeText(context, "Error al resetear las estadísticas", Toast.LENGTH_SHORT).show()
            }
    }



    // Gráfico circular de categorías
    private fun updatePieChart(categoriaStats: List<CategoriaStats>) {
        val entries = categoriaStats
            .filter { it.clickCount > 0 }
            .map { PieEntry(it.clickCount.toFloat(), it.categoriaName) }

        if (entries.isEmpty()) {
            binding.grafCategorias.clear()
            return
        }

        val pieDataSet = PieDataSet(entries, "Clicks por Categoría")
        pieDataSet.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.ramos),
            ContextCompat.getColor(requireContext(), R.color.plantaInt),
            ContextCompat.getColor(requireContext(), R.color.plantExt),
            ContextCompat.getColor(requireContext(), R.color.floresEv),
            ContextCompat.getColor(requireContext(), R.color.macetas),
            ContextCompat.getColor(requireContext(), R.color.pack)
        ).take(entries.size)

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

    // Gráfico de barras para compras
    private fun updateBarChart(compraStats: List<CompraStats>) {
        val entries = compraStats.mapIndexed { index, stat ->
            BarEntry(index.toFloat(), stat.precioTotal) // Se usa el nuevo atributo precioTotal
        }

        if (entries.isEmpty()) {
            binding.grafCompra.clear()
            return
        }

        val barDataSet = BarDataSet(entries, "Precios Totales de Compras")

        // Definir los colores de las barras. Puedes usar cualquier color aquí.
        val colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.color1),
            ContextCompat.getColor(requireContext(), R.color.color2),
            ContextCompat.getColor(requireContext(), R.color.color3),
            ContextCompat.getColor(requireContext(), R.color.color4)
        )

        // Asignar los colores a las barras
        barDataSet.colors = colors

        // Cambiar color del texto de los valores de las barras
        barDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        barDataSet.valueTextSize = 16f

        // Personalizar los valores que se muestran en las barras
        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = entries.indexOfFirst { it.y == value }
                return "Compra ${index + 1}" // Mostrar "Compra 1", "Compra 2", etc.
            }
        }

        binding.grafCompra.apply {
            data = BarData(barDataSet)
            description.isEnabled = false
            animateY(1000)
            invalidate()
        }
    }


}
