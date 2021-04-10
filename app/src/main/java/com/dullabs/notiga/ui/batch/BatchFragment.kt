package com.dullabs.notiga.ui.batch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.R
import com.dullabs.notiga.databinding.FragmentBatchBinding
import com.google.android.material.snackbar.Snackbar

class BatchFragment : Fragment() {

    private var _binding: FragmentBatchBinding? = null
    private lateinit var batchViewModel: BatchViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchBinding.inflate(inflater, container, false)
        batchViewModel =
            ViewModelProvider(this).get(BatchViewModel::class.java)
        batchViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textBatch.text = it
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setFABOnClickListener {
            Snackbar.make(view, "Here's a Snackbar from Batch Fragment", Snackbar.LENGTH_LONG)
                .setAction("Action", null).setAnchorView(R.id.bottomFab)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}