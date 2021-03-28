package com.dullabs.notiga.ui.inbox

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dullabs.notiga.R
import com.dullabs.notiga.adapters.NotificationWrapperAdapter
import com.dullabs.notiga.adapters.SimpleSectionedRecyclerViewAdapter
import com.dullabs.notiga.adapters.SimpleSectionedRecyclerViewAdapter.Section
import com.dullabs.notiga.databinding.FragmentInboxBinding
import com.dullabs.notiga.models.NotificationWrapper
import com.dullabs.notiga.ui.commons.BaseInboxFragment
import com.google.android.material.snackbar.Snackbar


class InboxFragment : BaseInboxFragment() {

    private var _binding: FragmentInboxBinding? = null
    private lateinit var inboxViewModel: InboxViewModel
    private lateinit var mNotificationWrapperAdapter: NotificationWrapperAdapter
    private lateinit var mSectionedAdapter: SimpleSectionedRecyclerViewAdapter

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
        inboxViewModel.getNotificationsWrapper().observe(viewLifecycleOwner, Observer {
            mNotificationWrapperAdapter.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerViewer() {
        mNotificationWrapperAdapter = NotificationWrapperAdapter(inboxViewModel.getNotificationsWrapper().value!!, requireContext()) {
            println("Notification item clicked: ${it.getLastNotification().getAppName()}")
            val action = InboxFragmentDirections.actionInboxFragmentToAppInboxFragment(it.getLastNotification().getAppName())
            findNavController().navigate(action)
        }
        //This is the code to provide a sectioned list
        val sections: ArrayList<Section> = ArrayList()
        //Sections
        sections.add(Section(0, "Section 1"))
        sections.add(Section(5, "Section 2"))
        sections.add(Section(12, "Section 3"))
        sections.add(Section(14, "Section 4"))
        sections.add(Section(20, "Section 5"))

        //Add your adapter to the sectionAdapter
        val dummy = arrayOfNulls<Section>(sections.size)
        mSectionedAdapter =
            SimpleSectionedRecyclerViewAdapter(requireContext(), R.layout.section, R.id.section_text, mNotificationWrapperAdapter)
        mSectionedAdapter.setSections(sections.toArray(dummy))
        binding.recyclerView.adapter = mSectionedAdapter
        enableSwipeDeleteAndUndo()
    }

    private fun enableSwipeDeleteAndUndo() {
        val swipeCallback: SwipeCallback = object : SwipeCallback(requireContext()) {
            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val sectionedPosition: Int = viewHolder.adapterPosition
                val originalPosition = mSectionedAdapter.sectionedPositionToPosition(sectionedPosition)
                val notificationWrapperItem: NotificationWrapper =
                    mNotificationWrapperAdapter.getItemAtPosition(originalPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    inboxViewModel.removeNotification(originalPosition)
                    snackbar = Snackbar.make(
                        binding.recyclerView,
                        "Item was removed from the list.",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("Undo") {
                        inboxViewModel.restoreNotification(originalPosition, notificationWrapperItem)
                        binding.recyclerView.scrollToPosition(sectionedPosition)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.setBackgroundTint(Color.DKGRAY)
                    snackbar.setAnchorView(R.id.bottomFab).show()
                } else {
                    mSectionedAdapter.notifyItemChanged(sectionedPosition)
                    mNotificationWrapperAdapter.notifyItemChanged(originalPosition)
                    snackbar = Snackbar.make(
                        binding.recyclerView,
                        "Item paused.",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setBackgroundTint(Color.DKGRAY)
                    snackbar.setAnchorView(R.id.bottomFab).show()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}