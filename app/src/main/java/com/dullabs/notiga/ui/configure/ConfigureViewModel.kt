package com.dullabs.notiga.ui.configure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigureViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Configure Fragment"
    }
    val text: LiveData<String> = _text
}