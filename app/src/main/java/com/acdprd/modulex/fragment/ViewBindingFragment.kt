package com.acdprd.modulex.fragment

import android.widget.Toast
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.FragmentVbBinding
import com.acdprd.modulex.fragment.basevb.BaseVbFragment
import com.acdprd.modulex.fragment.basevb.CreateMethod
import com.acdprd.modulex.fragment.basevb.viewBindingDelegate

class ViewBindingFragment : BaseVbFragment<FragmentVbBinding>() {

    override val binding: FragmentVbBinding by viewBindingDelegate()
    override fun getLayoutRes(): Int = R.layout.fragment_vb

    override fun onCreateView() {

        binding.tvText.setOnClickListener {
            Toast.makeText(binding.tvText.context, ":TOAST:", Toast.LENGTH_SHORT).show()
        }
    }
}