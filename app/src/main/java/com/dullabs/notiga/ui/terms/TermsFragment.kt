package com.dullabs.notiga.ui.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dullabs.notiga.R

class TermsFragment : Fragment() {

    private lateinit var termsViewModel: TermsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        termsViewModel =
            ViewModelProvider(this).get(TermsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_terms, container, false)
        val textView: TextView = root.findViewById(R.id.text_terms)
        termsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}