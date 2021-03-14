package com.dullabs.notiga.ui.appinbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.databinding.FragmentAppInboxBinding
import com.dullabs.notiga.databinding.FragmentBatchBinding

class AppInboxFragment : Fragment() {

    private var _binding: FragmentAppInboxBinding? = null
    private val appInboxViewModel: AppInboxViewModel by activityViewModels()
    private val appInboxFragmentArgs: AppInboxFragmentArgs by navArgs()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppInboxBinding.inflate(inflater, container, false)
        appInboxViewModel.setAppName(appInboxFragmentArgs.appName)
        appInboxViewModel.getAppName().observe(viewLifecycleOwner, Observer {
            binding.textAppInbox.text = it
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