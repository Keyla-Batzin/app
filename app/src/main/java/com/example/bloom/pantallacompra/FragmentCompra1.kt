package com.example.bloom.pantallacompra

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bloom.R
import com.example.bloom.compra.ComprasFragment
import com.example.bloom.compra.PrecioTotalResponse
import com.example.bloom.stats.CompraStats
import com.example.bloom.stats.SharedViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException

class FragmentCompra1 : Fragment() {

    private lateinit var precioTotalCompra: PrecioTotalResponse
    private lateinit var viewModel: SharedViewModel
    private val compraStatsList = mutableListOf<CompraStats>()
    private var idPrecio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            precioTotalCompra = it.getSerializable("precioTotalCompra") as PrecioTotalResponse
            // Log para mostrar el precio recibido
            Log.d("FragmentCompra1", "Precio total recibido: ${precioTotalCompra.precio_total}")
        }

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Crear un nuevo objeto CompraStats con ID generado y precioTotal convertido a Float
        val nuevaCompra = CompraStats(
            id = idPrecio++,  // Generar un ID único
            precioTotal = precioTotalCompra.precio_total.toFloat()  // Convertir precio_total a Float
        )

        compraStatsList.add(nuevaCompra)
        viewModel.compraStats.value = compraStatsList

        // Guardar el precio total en Firestore
        guardarPrecioTotalEnFirestore(nuevaCompra)
    }

    private fun guardarPrecioTotalEnFirestore(compra: CompraStats) {
        val db = FirebaseFirestore.getInstance()
        val compraData = hashMapOf(
            "id" to compra.id,
            "precioTotal" to compra.precioTotal
        )

        db.collection("stats").document("compraStats")
            .update("compras", FieldValue.arrayUnion(compraData))
            .addOnSuccessListener {
                Log.d("Firestore", "Compra guardada correctamente en Firestore")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error guardando la compra", e)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_compra1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Encuentra el Spinner (desplegable) que muestra la lista de países
        val countrySpinner: Spinner = view.findViewById(R.id.countrySpinner)

        // Crear un adaptador para el Spinner con el array de países
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.countries_array, // El array de países que está en strings.xml
            android.R.layout.simple_spinner_item
        )

        // Establecer el diseño del desplegable del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Asignar el adaptador al Spinner
        countrySpinner.adapter = adapter

        // Establecer un texto de ayuda que indica al usuario que seleccione un país
        countrySpinner.prompt = getString(R.string.select_country)

        // Encuentra el botón Continuar
        val btnNext = view.findViewById<Button>(R.id.btn_next)
        // Configura el listener para el botón "Continuar"
        btnNext.setOnClickListener {
            // Reemplaza el fragmento actual con el fragmento "FragmentCarrito"
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, FragmentCompra2())?.commit()
        }

        // Encuentra el botón Atras
        val btnBack = view.findViewById<ImageButton>(R.id.btn_back)
        // Configura el listener para el botón "Atras"
        btnBack.setOnClickListener {
            // Reemplaza el fragmento actual con el fragmento "FragmentCompra2"
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_fragment, ComprasFragment())?.commit()
        }

        // Actualizar la lista de compras en el ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val nuevaCompra = CompraStats(idPrecio ,precioTotalCompra.precio_total.toFloat())
                    if (compraStatsList.isEmpty()) {
                        compraStatsList.add(nuevaCompra)
                    }
                    viewModel.compraStats.value = compraStatsList
                } catch (e: CancellationException) {
                    Log.e("API", "La coroutine fue cancelada antes de completar la tarea")
                } catch (e: Exception) {
                    Log.e("API", "Error al pasar Compra a Stats", e)
                }
            }
        }
    }
}
