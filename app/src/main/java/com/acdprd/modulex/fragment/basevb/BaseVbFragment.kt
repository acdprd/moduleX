package com.acdprd.modulex.fragment.basevb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseVbFragment<B : ViewBinding> : Fragment() {

    protected abstract val binding: B

    @LayoutRes
    protected abstract fun getLayoutRes(): Int
    protected abstract fun onCreateView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = binding.root
        onCreateView()
        return root
    }
}

