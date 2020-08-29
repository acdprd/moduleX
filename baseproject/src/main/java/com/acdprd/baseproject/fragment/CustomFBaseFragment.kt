package com.acdprd.baseproject.fragment

import androidx.databinding.ViewDataBinding

abstract class CustomFBaseFragment<B : ViewDataBinding, F : Any> : CustomBaseFragment<B>() {
    protected abstract fun getFragmentType(): F

    override fun onResume() {
        super.onResume()
        onResumed(getFragmentType())
    }

    protected open fun onResumed(f: F) = Unit
}