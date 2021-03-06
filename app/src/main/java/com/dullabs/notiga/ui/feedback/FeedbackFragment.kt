package com.dullabs.notiga.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.databinding.FragmentFeedbackBinding

class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private lateinit var feedbackViewModel: FeedbackViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        feedbackViewModel =
            ViewModelProvider(this).get(FeedbackViewModel::class.java)
        feedbackViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textFeedback.text = it
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