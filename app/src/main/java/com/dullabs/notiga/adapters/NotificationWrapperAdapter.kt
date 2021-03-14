package com.dullabs.notiga.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dullabs.notiga.R
import com.dullabs.notiga.models.NotificationWrapper

class NotificationWrapperAdapter(
    private var mNotificationsWrapperData: List<NotificationWrapper>,
    private var mContext: Context,
    private val onNotificationWrapperClicked: (NotificationWrapper) -> Unit
) : RecyclerView.Adapter<NotificationWrapperAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mIconImage = itemView.findViewById<ImageView>(R.id.notificationWrapperIconImage)
        private val mAppName = itemView.findViewById<TextView>(R.id.notificationWrapperAppName)
        private val mNotificationDescription = itemView.findViewById<TextView>(R.id.notificationWrapperDescription)
        private val mNotificationWrapperCount = itemView.findViewById<TextView>(R.id.notificationWrapperCount)

        init {
            itemView.setOnClickListener {
                onNotificationWrapperClicked(mNotificationsWrapperData[adapterPosition])
            }
        }

        fun bindTo(currentNotificationWrapper: NotificationWrapper) {
            mIconImage.setImageResource(currentNotificationWrapper.getLastNotification().getAppIconId())
            mAppName.text = currentNotificationWrapper.getLastNotification().getAppName()
            mNotificationDescription.text = currentNotificationWrapper.getLastNotification().getNotificationDescription()
            mNotificationWrapperCount.text = currentNotificationWrapper.getCount().toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.notification_wrapper_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(currentNotificationWrapper = mNotificationsWrapperData[position])
    }

    override fun getItemCount(): Int {
        return mNotificationsWrapperData.size
    }

    fun getItemAtPosition(position: Int): NotificationWrapper {
        return mNotificationsWrapperData[position]
    }

}