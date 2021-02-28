package com.dullabs.notiga.ui.batch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BatchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Batch Fragment"
    }
    val text: LiveData<String> = _text
}