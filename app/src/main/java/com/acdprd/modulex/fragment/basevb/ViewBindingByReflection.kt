package com.acdprd.modulex.fragment.basevb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified VB : ViewBinding> ViewGroup.viewBindingDelegate(createMethod: CreateMethod = CreateMethod.INFLATE): ReadOnlyProperty<ViewGroup, VB> {
    val clz = VB::class.java
    return when (createMethod) {
        CreateMethod.INFLATE -> BindingByReflectionProperty(
            BindingByInflate(clz, extendedMethod = true, parent = this)
        ) { v ->
            LayoutInflater.from(v.context)
        }
        CreateMethod.BIND -> BindingByReflectionProperty(BindingByBind(clz)) { v -> v }
    }
}

inline fun <reified VB : ViewBinding> Fragment.viewBindingDelegate(createMethod: CreateMethod = CreateMethod.INFLATE): ReadOnlyProperty<Fragment, VB> {
    val clz = VB::class.java
    return when (createMethod) {
        CreateMethod.INFLATE -> BindingByReflectionProperty(BindingByInflate(clz)) { frg -> frg.layoutInflater }
        CreateMethod.BIND -> BindingByReflectionProperty(BindingByBind(clz)) { frg -> frg.view }
    }
}

private fun getMethod(
    clazz: Class<out Any>,
    methodName: String,
    vararg classes: Class<out Any>
): Method {
    return clazz.getMethod(methodName, *classes)
}

class BindingByBind<VB : ViewBinding>(val clazz: Class<VB>) :
    ((View?) -> VB) {

    private val methodName: String = CreateMethod.BIND.name.toLowerCase(Locale.getDefault())

    override fun invoke(p1: View?): VB {
        val method = getMethod(clazz, methodName, View::class.java)
        return method.invoke(clazz, p1) as VB
    }
}

class BindingByInflate<VB : ViewBinding>(
    private val clazz: Class<VB>,
    private var parent: ViewGroup? = null,
    private val extendedMethod: Boolean = DEFAULT_EXTENDED_METHOD
) :
    ((LayoutInflater) -> VB) {

    private val methodName: String = CreateMethod.INFLATE.name.toLowerCase(Locale.getDefault())

    override fun invoke(p1: LayoutInflater): VB {
        return MethodInvoker(
            clazz,
            methodName,
            extendedMethod,
            parent
        ).inflate(p1) as VB
    }

    private class MethodInvoker(
        private val clazz: Class<out Any>,
        private val methodName: String,
        private val extendedMethod: Boolean = DEFAULT_EXTENDED_METHOD,
        private var parent: ViewGroup? = null,
    ) {

        fun inflate(layoutInflater: LayoutInflater): ViewBinding {
            return if (extendedMethod) inflate3(layoutInflater) else inflate1(layoutInflater)
        }

        fun inflate3(layoutInflater: LayoutInflater): ViewBinding {
            val method =
                getMethod(
                    clazz,
                    methodName,
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.java
                )
            return method.invoke(clazz, layoutInflater, parent, parent != null) as ViewBinding
        }

        fun inflate1(layoutInflater: LayoutInflater): ViewBinding {
            val method = getMethod(clazz, methodName, LayoutInflater::class.java)
            return method.invoke(clazz, layoutInflater) as ViewBinding
        }
    }

    companion object {
        const val DEFAULT_EXTENDED_METHOD = false
    }
}

class BindingByReflectionProperty<TR, BP, VB : ViewBinding>(
    private val binder: ((BP) -> VB),
    private val bindingParam: ((TR) -> BP)
) : ReadOnlyProperty<TR, VB> {
    private var field: VB? = null

    //todo w/ lifecycle
    override fun getValue(thisRef: TR, property: KProperty<*>): VB {
        val _f = field
        if (_f != null) {
            return _f
        } else {
            val param = bindingParam.invoke(thisRef)
            return binder.invoke(param).also {
                field = it
            }
        }

    }
}

enum class CreateMethod {
    BIND,
    INFLATE, ;
}