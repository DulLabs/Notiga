package com.dullabs.notiga.ui.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.R
import com.dullabs.notiga.databinding.FragmentTermsBinding

class TermsFragment : Fragment() {

    private var _binding: FragmentTermsBinding? = null
    private lateinit var termsViewModel: TermsViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTermsBinding.inflate(inflater, container, false)
        termsViewModel =
            ViewModelProvider(this).get(TermsViewModel::class.java)
        termsViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textTerms.text = it
        })
        hideBottomControls()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomControls()
        _binding = null
    }

    private fun hideBottomControls() {
        (activity as MainActivity).hideBottomAppBar()
        (activity as MainActivity).hideBottomFab()
    }

    private fun showBottomControls() {
        (activity as MainActivity).showBottomAppBar()
        (activity as MainActivity).showBottomFab()
    }

}