package com.dullabs.notiga.ui.configure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.R

class ConfigureFragment : Fragment() {

    private lateinit var configureViewModel: ConfigureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureViewModel =
            ViewModelProvider(this).get(ConfigureViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_configure, container, false)
        val textView: TextView = root.findViewById(R.id.textConfigure)
        configureViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}