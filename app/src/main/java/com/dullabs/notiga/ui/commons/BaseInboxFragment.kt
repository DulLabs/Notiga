package com.dullabs.notiga.ui.commons

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseInboxFragment: Fragment() {
    protected lateinit var snackbar: Snackbar
    override fun onDestroyView() {
        super.onDestroyView()
        if (this::snackbar.isInitialized && snackbar.isShown) {
            snackbar.dismiss()
        }
    }
}