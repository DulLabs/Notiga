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
    private var mContext: Context,
    private val onNotificationClicked: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mIconImage = itemView.findViewById<ImageView>(R.id.iconImage)
        private val mAppName = itemView.findViewById<TextView>(R.id.appName)
        private val mNotificationDescription = itemView.findViewById<TextView>(R.id.notificationDescription)

        init {
            itemView.setOnClickListener {
                onNotificationClicked(mNotificationsData[adapterPosition])
            }
        }

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

    fun getItemAtPosition(position: Int): Notification {
        return mNotificationsData[position]
    }

}