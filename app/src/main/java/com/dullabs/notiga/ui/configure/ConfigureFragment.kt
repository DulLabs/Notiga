package com.dullabs.notiga.ui.configure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.R
import com.dullabs.notiga.databinding.FragmentConfigureBinding
import com.google.android.material.snackbar.Snackbar

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFABOnClickListener {
            Snackbar.make(view, "Here's a Snackbar from Configure Fragment", Snackbar.LENGTH_LONG)
                .setAction("Action", null).setAnchorView(R.id.bottomFab)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}