package com.dullabs.notiga.ui.appinbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dullabs.notiga.models.Notification
import com.dullabs.notiga.repositories.NotificationRepository

class AppInboxViewModel : ViewModel() {

    private lateinit var _mNotifications: MutableLiveData<ArrayList<Notification>>
    private lateinit var mRepo: NotificationRepository
    private val _appName = MutableLiveData<String>()

    fun getNotifications(): LiveData<ArrayList<Notification>> {
        return _mNotifications
    }

    fun init(appName: String) {
        if (!this::_mNotifications.isInitialized) {
            mRepo = NotificationRepository.getInstance()
        }
        setAppName(appName)
        _mNotifications = mRepo.getNotifications(appName)
    }

    fun removeNotification(position: Int) {
        // This part updates data base or api call
//        mRepo.removeNotification(position)

        val currentNotifications: ArrayList<Notification> = _mNotifications.value!!
        currentNotifications.removeAt(position)
        _mNotifications.postValue(currentNotifications)
    }

    fun restoreNotification(position: Int, notification: Notification) {
        // This part updates data base or api call
//        mRepo.restoreNotification(position, notification)

        val currentNotifications: ArrayList<Notification> = _mNotifications.value!!
        currentNotifications.add(position, notification)
        _mNotifications.postValue(currentNotifications)
    }

    fun getAppName(): LiveData<String> {
        return _appName
    }

    private fun setAppName(name: String) {
        _appName.postValue(name)
    }
}