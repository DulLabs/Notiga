package com.dullabs.notiga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dullabs.notiga.R
import com.dullabs.notiga.models.Notification

class NotificationAdapter(
    private var mNotificationsData: List<Notification>,
    private var mContext: Context
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mIconImage = itemView.findViewById<ImageView>(R.id.iconImage)
        private val mAppName = itemView.findViewById<TextView>(R.id.appName)
        private val mNotificationDescription = itemView.findViewById<TextView>(R.id.notificationDescription)

        fun bindTo(currentNotification: Notification) {
            mIconImage.setImageResource(currentNotification.getAppIconId())
            mAppName.text = currentNotification.getAppName()
            mNotificationDescription.text = currentNotification.getNotificationDescription()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(currentNotification = mNotificationsData[position])
    }

    override fun getItemCount(): Int {
        return mNotificationsData.size
    }

//    fun addItem(notificationItem: Notification) {
//        mNotificationsData.add(notificationItem)
//        notifyDataSetChanged()
//    }
//
//    fun removeItem(position: Int) {
//        mNotificationsData.removeAt(position)
//        notifyItemRemoved(position)
//    }
//
//    fun restoreItem(notificationItem: Notification, position: Int) {
//        mNotificationsData.add(position, notificationItem)
//        notifyItemInserted(position)
//    }

    fun getData(): List<Notification> {
        return mNotificationsData
    }

    fun getItemAtPosition(position: Int): Notification {
        return mNotificationsData[position]
    }
}