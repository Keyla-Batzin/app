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
        db = FirebaseFirestore.getInstance()

        // Añadir logs para depuración
        Log.d("StatsFragment", "Fragment iniciado correctamente")

        // Obtener y mostrar el contador de inicios de sesión
        stats.getLoginCount { loginCount ->
            binding.txtInicioSesion.text = loginCount.toString()
            Log.d("StatsFragment", "Login count obtenido: $loginCount")
        }

        // Obtener estadísticas de compras
        stats.getCompraStats { compraStats ->
            Log.d("StatsFragment", "Compra stats recibidos: ${compraStats.size}")
            viewModel.compraStats.postValue(compraStats)
        }

        // Obtener estadísticas de categorias
        stats.getCategoriaStats{ categoriaStats ->
            Log.d("StatsFragment", "Categoria stats recibidos: ${categoriaStats.size}")
            viewModel.categoriaStats.postValue(categoriaStats)
        }

        // Observar cambios en las estadísticas de categorías y actualizar el gráfico
        viewModel.categoriaStats.observe(viewLifecycleOwner) { categoriaStats ->
            Log.d("StatsFragment", "Actualizando gráfico de categorías con ${categoriaStats.size} ítems")
            updatePieChart(categoriaStats)
        }

        // Observar cambios en las estadísticas de compras y actualizar el gráfico
        viewModel.compraStats.observe(viewLifecycleOwner) { compraStats ->
            Log.d("StatsFragment", "Actualizando gráfico de compras con ${compraStats.size} ítems")
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

        // Referencias a los documentos en Firestore
        val categoriaStatsRef = db.collection("stats").document("categoriaStats")
        val compraStatsRef = db.collection("stats").document("compraStats")
        val loginCountRef = db.collection("stats").document("loginCount")

        // Borrar el contenido de los documentos sin eliminarlos
        val emptyData = mapOf<String, Any>("categorias" to listOf<Any>()) // Inicializar con lista vacía en lugar de mapa vacío

        categoriaStatsRef.set(emptyData)
            .addOnSuccessListener {
                Log.d("StatsFragment", "Estadísticas de categorías reseteadas correctamente.")
            }
            .addOnFailureListener { e ->
                Log.w("StatsFragment", "Error reseteando estadísticas de categorías", e)
            }

        compraStatsRef.set(mapOf<String, Any>("compras" to listOf<Any>()))
            .addOnSuccessListener {
                Log.d("StatsFragment", "Estadísticas de compras reseteadas correctamente.")
            }
            .addOnFailureListener { e ->
                Log.w("StatsFragment", "Error reseteando estadísticas de compras", e)
            }

        // Reiniciar numLogin a 0 en lugar de eliminarlo
        loginCountRef.update("numLogin", 0)
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

                // Actualizar la UI del contador de inicios de sesión
                binding.txtInicioSesion.text = "0"

                // Limpiar los LiveData
                viewModel.categoriaStats.postValue(emptyList())
                viewModel.compraStats.postValue(emptyList())
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
            Log.d("StatsFragment", "No hay entradas para el gráfico de categorías")
            binding.grafCategorias.setNoDataText("No hay datos disponibles")
            binding.grafCategorias.invalidate()
            return
        }

        Log.d("StatsFragment", "Creando gráfico con ${entries.size} entradas")

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

        val pieData = PieData(pieDataSet)

        // Opcional: Personalizar formato de valores
        pieData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString() // Mostrar valores sin decimales
            }
        })

        binding.grafCategorias.apply {
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(ContextCompat.getColor(requireContext(), R.color.white))
            holeRadius = 40f
            transparentCircleRadius = 45f
            setTransparentCircleColor(ContextCompat.getColor(requireContext(), R.color.grey))
            setTransparentCircleAlpha(110)
            legend.isEnabled = true // Mostrar leyenda
            setEntryLabelColor(ContextCompat.getColor(requireContext(), R.color.black))
            setEntryLabelTextSize(12f)
            animateY(1000)
            invalidate()
        }
    }

    // Gráfico de barras para compras
    private fun updateBarChart(compraStats: List<CompraStats>) {
        val entries = compraStats.mapIndexed { index, stat ->
            BarEntry(index.toFloat(), stat.precioTotal)
        }

        if (entries.isEmpty()) {
            Log.d("StatsFragment", "No hay entradas para el gráfico de compras")
            binding.grafCompra.setNoDataText("No hay datos disponibles")
            binding.grafCompra.invalidate()
            return
        }

        Log.d("StatsFragment", "Creando gráfico de barras con ${entries.size} entradas")

        val barDataSet = BarDataSet(entries, "Precios Totales de Compras")

        // Definir los colores de las barras
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
        val barData = BarData(barDataSet)
        barData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "€${value.toInt()}" // Añadir símbolo de moneda
            }
        })

        binding.grafCompra.apply {
            data = barData
            description.isEnabled = false
            xAxis.granularity = 1f
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.toInt()
                    return if (index < compraStats.size) {
                        "Compra ${index + 1}"
                    } else {
                        ""
                    }
                }
            }
            animateY(1000)
            invalidate()
        }
    }
}