package com.dullabs.notiga.ui.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dullabs.notiga.models.Notification
import com.dullabs.notiga.models.NotificationWrapper
import com.dullabs.notiga.repositories.NotificationRepository
import com.dullabs.notiga.repositories.NotificationWrapperRepository

class InboxViewModel : ViewModel() {

    private lateinit var _mNotificationsWrapper: MutableLiveData<ArrayList<NotificationWrapper>>
    private lateinit var mRepo: NotificationWrapperRepository

    fun getNotificationsWrapper(): LiveData<ArrayList<NotificationWrapper>> {
        return _mNotificationsWrapper
    }


    fun init() {
        if (this::_mNotificationsWrapper.isInitialized) {
            return
        }
        mRepo = NotificationWrapperRepository.getInstance()
        _mNotificationsWrapper = mRepo.getNotificationsWrapper()
        println("Initialized notifications in view, size is ${getNotificationsWrapper().value!!.size}")
    }

    fun removeNotification(position: Int) {
        // This part updates data base or api call
//        mRepo.removeNotification(position)

        val currentNotifications: ArrayList<NotificationWrapper> = _mNotificationsWrapper.value!!
        currentNotifications.removeAt(position)
        _mNotificationsWrapper.postValue(currentNotifications)
    }

    fun restoreNotification(position: Int, notificationWrapper: NotificationWrapper) {
        // This part updates data base or api call
//        mRepo.restoreNotification(position, notification)

        val currentNotifications: ArrayList<NotificationWrapper> = _mNotificationsWrapper.value!!
        currentNotifications.add(position, notificationWrapper)
        _mNotificationsWrapper.postValue(currentNotifications)
    }

}