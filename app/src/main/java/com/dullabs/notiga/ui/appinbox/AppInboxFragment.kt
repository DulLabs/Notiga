package com.dullabs.notiga.ui.appinbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.databinding.FragmentAppInboxBinding
import com.dullabs.notiga.databinding.FragmentBatchBinding

class AppInboxFragment : Fragment() {

    private var _binding: FragmentAppInboxBinding? = null
    private lateinit var appInboxViewModel: AppInboxViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppInboxBinding.inflate(inflater, container, false)
        appInboxViewModel =
            ViewModelProvider(this).get(AppInboxViewModel::class.java)
        appInboxViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textAppInbox.text = it
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