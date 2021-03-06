package com.dullabs.notiga.ui.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.adapters.NotificationAdapter
import com.dullabs.notiga.databinding.FragmentInboxBinding

class InboxFragment : Fragment() {

    private var _binding: FragmentInboxBinding? = null
    private lateinit var inboxViewModel: InboxViewModel
    private lateinit var mNotificationAdapter: NotificationAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentInboxBinding.inflate(inflater, container, false)
        inboxViewModel = ViewModelProvider(this).get(InboxViewModel::class.java)
        inboxViewModel.init()
        inboxViewModel.getNotifications().observe(viewLifecycleOwner, Observer {
            mNotificationAdapter.notifyDataSetChanged()
        })
        setupRecyclerViewer()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerViewer() {
        mNotificationAdapter = NotificationAdapter(inboxViewModel.getNotifications().value!!, requireContext())
        binding.recyclerView.adapter = mNotificationAdapter
//        enableSwipeDeleteAndUndo()
    }
}