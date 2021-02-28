package com.dullabs.notiga.ui.chatoptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatOptionsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Chat Options Fragment"
    }
    val text: LiveData<String> = _text
}