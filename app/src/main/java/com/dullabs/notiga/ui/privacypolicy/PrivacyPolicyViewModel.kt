package com.dullabs.notiga.ui.privacypolicy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrivacyPolicyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Privacy policy Fragment"
    }
    val text: LiveData<String> = _text
}