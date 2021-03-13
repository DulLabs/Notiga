package com.dullabs.notiga.ui.insights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.databinding.FragmentInsightsBinding

class InsightsFragment : Fragment() {

    private var _binding: FragmentInsightsBinding? = null
    private lateinit var insightsViewModel: InsightsViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInsightsBinding.inflate(inflater, container, false)
        insightsViewModel =
            ViewModelProvider(this).get(InsightsViewModel::class.java)
        insightsViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textInsights.text = it
        })
        (activity as MainActivity).hideBottomControls()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomControls()
        _binding = null
    }

}