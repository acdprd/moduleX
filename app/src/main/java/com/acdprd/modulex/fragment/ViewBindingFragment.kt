package com.acdprd.modulex.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.FragmentVbBinding
import com.acdprd.modulex.fragment.basevb.BaseVbFragment
import com.acdprd.modulex.fragment.basevb.CreateMethod
import com.acdprd.modulex.fragment.basevb.viewBinding

class ViewBindingFragment : BaseVbFragment<FragmentVbBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_vb
    override fun initBinding(inflater: LayoutInflater, v: ViewGroup?) {
//        super.initBinding(inflater, v)
        binding = FragmentVbBinding.inflate(inflater, v, false)
    }

    override fun onCreateView() {

        binding.tvText.setOnClickListener {
            Toast.makeText(binding.tvText.context, ":TOAST:", Toast.LENGTH_SHORT).show()
        }

        val a: FragmentVbBinding = viewBinding(CreateMethod.BIND)
        Log.w("AAAA",a::class.java.simpleName)
    }

}