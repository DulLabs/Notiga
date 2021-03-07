package com.dullabs.notiga.ui.appinbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppInboxViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is App Inbox Fragment"
    }
    val text: LiveData<String> = _text
}