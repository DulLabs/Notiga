package com.dullabs.notiga.ui.privacypolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {

    private var _binding: FragmentPrivacyPolicyBinding? = null
    private lateinit var privacyPolicyViewModel: PrivacyPolicyViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        privacyPolicyViewModel =
            ViewModelProvider(this).get(PrivacyPolicyViewModel::class.java)
        privacyPolicyViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textPrivacyPolicy.text = it
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