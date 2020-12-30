package com.acdprd.modulex.fragment

import androidx.navigation.fragment.findNavController
import com.acdprd.modulex.R
import com.acdprd.modulex.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_main

    override fun onCreateView() {
        setListeners()
    }

    private fun setListeners() {
        binding.tvToList.setOnClickListener { findNavController().navigate(R.id.action_forward_list) }
        binding.tvToViewBinding.setOnClickListener { findNavController().navigate(R.id.action_forward_vb) }
    }
}