package com.dullabs.notiga.ui.appinbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppInboxViewModel : ViewModel() {

    private val _appName = MutableLiveData<String>()

    fun getAppName(): LiveData<String> {
        return _appName
    }

    fun setAppName(name: String) {
        _appName.postValue(name)
    }
}