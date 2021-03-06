package com.dullabs.notiga.ui.inbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dullabs.notiga.models.Notification
import com.dullabs.notiga.repositories.NotificationRepository

class InboxViewModel : ViewModel() {

    private lateinit var _mNotifications: MutableLiveData<List<Notification>>
    private lateinit var mRepo: NotificationRepository

    fun getNotifications(): LiveData<List<Notification>> {
        return _mNotifications
    }


    fun init() {
        if (this::_mNotifications.isInitialized) {
            return
        }
        mRepo = NotificationRepository.getInstance()
        _mNotifications = mRepo.getNotifications()
        println("Initialized notifications in view, size is ${getNotifications().value!!.size}")
    }

}