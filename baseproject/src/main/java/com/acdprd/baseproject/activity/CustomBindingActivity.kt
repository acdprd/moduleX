package com.acdprd.baseproject.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.acdprd.baseproject.helpers.HideKeyboard

@Suppress("MemberVisibilityCanBePrivate")
abstract class CustomBindingActivity<B : ViewDataBinding> : CustomBaseActivity() {
    protected lateinit var binding: B
    protected lateinit var navController: NavController

    override fun create(savedInstanceState: Bundle?) {
        initBinding()
        setupNavController()
        create()
    }

//    abstract fun getActivityBinding():B

    private fun initBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutRes(), null, false)
//        binding = getActivityBinding().inflate(layoutInflater)
        setContentView(binding.root)
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getHostFragmentId(): Int

    protected abstract fun create()


    protected open fun setupNavController() {
        navController = findNavController(getHostFragmentId())
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            HideKeyboard.hideKeyboardForced(this)
            onNavControllerDestinationChanged(controller, destination, arguments)
        }
    }

    protected open fun onNavControllerDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) = Unit

    override fun onBackPressed() {
        val canBack = navController.previousBackStackEntry != null
        if (!canBack) {
            exit()
        } else {
            navController.navigateUp()
        }
    }

    protected open fun exit() {
        this.finish()
    }
}