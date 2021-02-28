package com.dullabs.notiga.ui.configure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.databinding.FragmentConfigureBinding

class ConfigureFragment : Fragment() {

    private var _binding: FragmentConfigureBinding? = null
    private lateinit var configureViewModel: ConfigureViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfigureBinding.inflate(inflater, container, false)
        configureViewModel =
            ViewModelProvider(this).get(ConfigureViewModel::class.java)
        configureViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textConfigure.text = it
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}