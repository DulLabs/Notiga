package com.dullabs.notiga.repositories

import androidx.lifecycle.MutableLiveData
import com.dullabs.notiga.R
import com.dullabs.notiga.models.Notification
import com.dullabs.notiga.models.NotificationWrapper

class NotificationWrapperRepository {
    companion object {
        private lateinit var instance: NotificationWrapperRepository
        fun getInstance(): NotificationWrapperRepository {
            if (!this::instance.isInitialized) {
                instance = NotificationWrapperRepository()
            }
            return instance
        }
    }

    private val dataSet = ArrayList<NotificationWrapper>()

    fun getNotificationsWrapper(): MutableLiveData<ArrayList<NotificationWrapper>> {
        setNotificationsWrapper()
        val data = MutableLiveData<ArrayList<NotificationWrapper>>()
        data.value = dataSet
        return data
    }

    private fun setNotificationsWrapper() {
        val apps = listOf("chrome", "whatsapp", "linkedin")
        for (i in 1..35) {
            dataSet.add(
                NotificationWrapper (
                    Notification(
                        R.drawable.ic_whatsapp,
                        apps[(0..2).random()],
                        "$i We have some crap that you want to check out."
                    ),
                    (1..5).random()
                )
            )
        }
    }

}