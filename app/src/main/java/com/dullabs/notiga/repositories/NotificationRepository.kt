package com.dullabs.notiga.repositories

import androidx.lifecycle.MutableLiveData
import com.dullabs.notiga.R
import com.dullabs.notiga.models.Notification

class NotificationRepository {

    companion object {
        private lateinit var instance: NotificationRepository
        fun getInstance(): NotificationRepository {
            if (!this::instance.isInitialized) {
                instance = NotificationRepository()
            }
            return instance
        }
    }

    private val dataSet = ArrayList<Notification>()

    fun getNotifications(): MutableLiveData<ArrayList<Notification>> {
        setNotifications()
        val data  = MutableLiveData<ArrayList<Notification>>()
        data.value = dataSet
        return data
    }

    private fun setNotifications() {
        for (i in 1..10) {
            dataSet.add(
                Notification(
                R.drawable.ic_whatsapp,
                "Chrome $i",
                "$i We have some crap that you want to check out."
            ))
        }
    }

    fun removeNotification(position: Int) {
        dataSet.removeAt(position)
    }

    fun restoreNotification(position: Int, notification: Notification) {
        dataSet.add(position, notification)
    }

}