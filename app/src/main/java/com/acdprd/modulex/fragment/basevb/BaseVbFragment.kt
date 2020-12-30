package com.acdprd.modulex.fragment.basevb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseVbFragment<B : ViewBinding> : Fragment() {
    //    protected var b : B by viewBinding()
    protected lateinit var binding: B /*= viewBinding(CreateMethod.BIND)*/
//    protected val binding: B
//        get() {
//            return _binding!!
//        }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int
    protected abstract fun onCreateView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels(getCommonViewModelProvider())
    }

    protected open fun initViewModels(viewModelProvider: ViewModelProvider) = Unit

    protected open fun getCommonViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding(inflater, container)
        val root = binding.root
        onCreateView()
        return root
    }

    protected open fun initBinding(inflater: LayoutInflater, v: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), v, false)
    }

    protected open fun debugBinding() {

    }

    protected open fun insets() = Unit

    protected open fun back() {
        if (isAdded) {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }
}

//todo as Property<,>
inline fun <reified VB : ViewBinding> /*Fragment.*/viewBinding(createMethod: CreateMethod): VB {
    val clz = VB::class.java
    val method = clz.getMethod(createMethod.name.toLowerCase(), View::class.java)
    val res = when (createMethod) {
        CreateMethod.BIND -> method.invoke(clz,null)
        CreateMethod.INFLATE -> method.invoke(clz)
    }
    return res as VB
}

enum class CreateMethod {
    BIND,
    INFLATE, ;
}