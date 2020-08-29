package com.acdprd.baseproject.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.acdprd.baseproject.helpers.ArgsBuilder
import kotlin.reflect.KFunction

abstract class CustomBaseFragment<B : ViewDataBinding> : Fragment() {
    protected lateinit var binding: B
    private var permissionListener: ((Int, IntArray) -> Unit)? = null
    private var onActivityResultListener: ((requestCode: Int, resultCode: Int, data: Intent?) -> Unit)? =
        null

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

    fun setOnActivityResultListener(onActivityResultListener: PreferenceManager.OnActivityResultListener) {
        this.onActivityResultListener = { requestCode, resultCode, data ->
            onActivityResultListener.onActivityResult(
                requestCode,
                resultCode,
                data
            )
        }
    }

    fun setOnActivityResultListener(l: (Int, Int, data: Intent?) -> Unit) {
        this.onActivityResultListener = l
    }

    fun setPermissionListener(permissionListener: ((Int, IntArray) -> Unit)?) {
        this.permissionListener = permissionListener
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionResponse(requestCode, grantResults)
    }

    protected open fun handlePermissionResponse(requestCode: Int, grantResults: IntArray) {
        permissionListener?.invoke(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultListener?.invoke(requestCode, resultCode, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        insets()
    }

    protected open fun insets() = Unit

    protected open fun back() {
        if (isAdded) {
            activity?.onBackPressed()
        }
    }

    protected open fun byArgsBuilder(arguments: Bundle? = this.arguments): ArgsBuilder {
        return ArgsBuilder.create(arguments)
    }

//    fun <T> Single<T>.useDisposable(): Single<T> {
//        return this.doOnSubscribe { disposables.add(it) }
//    }
//
//    protected open fun <L : LiveData<T>, T : Any?> L.observeOrRequestIfNull(
//        requestFun: KFunction<Single<*>>, vararg params: Any?,
//        observe: (T) -> Unit
//    ) {
//        this.observeOrRequestIfNull(requestFun.call(*params), observe)
//    }
//
//    protected open fun <L : LiveData<T>, T : Any?> L.observeOrRequestIfNull(
//        requestFun: Single<*>,
//        observe: (T) -> Unit
//    ) {
//        this.observe(this@CustomBaseFragment, Observer<T?> { o ->
//            if (o == null) {
//                requestFun.useDisposable().subscribe(SimpleSingle())
//            } else {
//                observe.invoke(o)
//            }
//        })
//    }
}