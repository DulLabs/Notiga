package com.dullabs.notiga.ui.faq

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FaqViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Faq Fragment"
    }
    val text: LiveData<String> = _text
}