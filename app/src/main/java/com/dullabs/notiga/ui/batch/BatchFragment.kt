package com.dullabs.notiga.ui.batch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.R

class BatchFragment : Fragment() {

    private lateinit var batchViewModel: BatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        batchViewModel =
            ViewModelProvider(this).get(BatchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_batch, container, false)
        val textView: TextView = root.findViewById(R.id.text_batch)
        batchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}