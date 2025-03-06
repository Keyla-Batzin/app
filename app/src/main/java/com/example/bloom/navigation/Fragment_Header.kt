package com.example.bloom.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bloom.R
import com.example.bloom.compra.ComprasFragment
import com.example.bloom.pantallacompra.ActivityCompra
import com.example.bloom.pantallacompra.FragmentCarrito

class Fragment_Header : Fragment() {

    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_header, container, false)

        // Configurar el Toolbar como ActionBar
        toolbar = view.findViewById(R.id.main_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Esto permite que el Fragmento gestione un menú
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu) // Infla el menú
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.carrito -> {
                val intent = Intent(requireContext(), ActivityCompra::class.java)
                intent.putExtra("fragment_a_mostrar", "ComprasFragment")
                startActivity(intent)
                return true
            }
            R.id.op1 -> {
                // Acción para opción 1 (De momento no tocarlos)
                return true
            }
            R.id.op2 -> {
                // Acción para opción 2 (De momento no tocarlos)
                return true
            }
            R.id.op3 -> {
                // Acción para opción 3 (De momento no tocarlos)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Cerrar todas las actividades
    //finishAffinity()

    // Limpiar la pila de fragmentos (si estás en una actividad que maneja fragmentos)
    // supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    // Iniciar una nueva actividad (opcional)
    // val intent = Intent(requireContext(), ActivityCompra::class.java)
    // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    // startActivity(intent)

    //activity?.supportFragmentManager?.beginTransaction()
    //?.replace(R.id.main_fragment, ComprasFragment())?.commit()
}
