package com.dullabs.notiga.ui.appinbox

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dullabs.notiga.MainActivity
import com.dullabs.notiga.R
import com.dullabs.notiga.adapters.NotificationAdapter
import com.dullabs.notiga.databinding.FragmentAppInboxBinding
import com.dullabs.notiga.models.Notification
import com.google.android.material.snackbar.Snackbar

class AppInboxFragment : Fragment() {

    private var _binding: FragmentAppInboxBinding? = null
    private val appInboxViewModel: AppInboxViewModel by activityViewModels()
    private val appInboxFragmentArgs: AppInboxFragmentArgs by navArgs()
    private lateinit var mNotificationAdapter: NotificationAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppInboxBinding.inflate(inflater, container, false)
        appInboxViewModel.init(appInboxFragmentArgs.appName)
        appInboxViewModel.getNotifications().observe(viewLifecycleOwner, Observer {
            mNotificationAdapter.notifyDataSetChanged()
        })
        (activity as MainActivity).hideBottomControls()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appInboxHeaderText.text = appInboxFragmentArgs.appName
        binding.appInboxHeaderIconImage.setImageResource(R.drawable.ic_whatsapp)
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomControls()
        _binding = null
    }

    private fun setupRecyclerView() {
        mNotificationAdapter = NotificationAdapter(appInboxViewModel.getNotifications().value!!, requireContext()) {
            //TODO: perform some action on notification clicked -> open the intended app
        }
        binding.appInboxRecyclerView.adapter = mNotificationAdapter
        enableSwipeDeleteAndUndo()
    }

    private fun enableSwipeDeleteAndUndo() {
        val swipeCallback: AppInboxSwipeCallback = object : AppInboxSwipeCallback(requireContext()) {
            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                val notificationItem: Notification = mNotificationAdapter.getItemAtPosition(position)
                appInboxViewModel.removeNotification(position)
                val snackbar: Snackbar = Snackbar.make(
                    (activity as MainActivity).getMainBinding().coordinatorLayout,
                    "Item was removed from the list.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("Undo") {
                    appInboxViewModel.restoreNotification(position, notificationItem)
                    binding.appInboxRecyclerView.scrollToPosition(position)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.setBackgroundTint(Color.DKGRAY)
                snackbar.show()
//                snackbar.setAnchorView(R.id.bottomFab).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.appInboxRecyclerView)
    }

}