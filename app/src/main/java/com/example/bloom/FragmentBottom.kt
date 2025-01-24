package com.example.bloom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bloom.databinding.FragmentBottomBinding

class FragmentBottom : Fragment() {
    private var _binding: FragmentBottomBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño usando View Binding
        _binding = FragmentBottomBinding.inflate(inflater, container, false)
        val view = binding?.root

        // Configurar el BottomNavigationView
        binding?.bottomNavigationView?.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.fav -> replaceFragment(FragmentFavoritos())
                R.id.profile -> replaceFragment(FragmentProfile())
                else -> false
            }
            true
        }

        // Mostrar el fragmento inicial
        if (savedInstanceState == null) {
            binding?.bottomNavigationView?.selectedItemId = R.id.home
            replaceFragment(HomeFragment())
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment) // Asegúrate de usar el ID correcto del contenedor
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
