package com.acdprd.baseproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.acdprd.baseproject.behaviors.activity.FullScreenBehavior

@Suppress("MemberVisibilityCanBePrivate")
abstract class CustomBaseActivity : AppCompatActivity() {
    protected var permissionListener: ((Int, IntArray) -> Unit)? = null


    protected open fun withFullScreen(): Boolean = false

    private fun beforeCreated(savedInstanceState: Bundle?) {
        if (withFullScreen()) FullScreenBehavior().init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeCreated(savedInstanceState)
        super.onCreate(savedInstanceState)
        initViewModels(getViewModelProvider())
        create(savedInstanceState)
        insets()
    }

    protected open fun insets() = Unit

    protected open fun initViewModels(viewModelProvider: ViewModelProvider) = Unit

    protected open fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
    }

    abstract fun create(savedInstanceState: Bundle?)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionResponse(requestCode, grantResults)
    }

    protected open fun handlePermissionResponse(requestCode: Int, grantResults: IntArray) {
        permissionListener?.invoke(requestCode, grantResults)
    }
}