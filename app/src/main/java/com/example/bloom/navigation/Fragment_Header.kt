package com.example.bloom.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bloom.R
import com.example.bloom.compra.ComprasFragment
import com.example.bloom.pantallahome.FragmentPreferencias
import com.example.bloom.stats.StatsFragment

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
                // Limpiar la pila de retroceso (back stack) si es necesario
                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, ComprasFragment(), "TAG_COMPRAS_FRAGMENT")
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.op1 -> {
                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, FragmentPreferencias(), "TAG_PREFERENCIAS_FRAGMENT")
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.op2 -> {
                parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, StatsFragment(), "TAG_STATS")
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.op3 -> {
                // Acción para opción 3 (De momento no tocarlos)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
