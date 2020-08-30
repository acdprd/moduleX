package com.acdprd.baseproject.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.reflect.KClass

fun CharSequence?.isEmptyOrBlankOrNull(): Boolean {
    return this.isNullOrEmpty() || this.isNullOrBlank()
}

fun CharSequence?.isNotEmptyNotBlankNotNull(): Boolean {
    return this != null && this.isNotEmpty() && this.isNotBlank()
}

fun <E : Enum<E>> KClass<E>.parseOrDefault(type: String?, defaultType: E): E {
    return this.java.enumConstants?.find { it.name.equals(type, true) } ?: defaultType
}

fun <L : LiveData<T>, T : Any> L.observeNotNull(owner: LifecycleOwner, observe: (T) -> Unit) {
    this.observe(owner, Observer<T> { o -> o?.let { observe.invoke(it) } })
}

fun <L : LiveData<T>, T : Any?> L.observeIt(owner: LifecycleOwner, observe: (T?) -> Unit) {
    this.observe(owner, Observer<T?> { o -> observe.invoke(o) })
}

fun <T : Any> T.tag(): String = this.javaClass.simpleName

fun <T : Any> Class<T>.tag(): String = this.javaClass.simpleName

/**
 * вернет процент этого числа от тотала
 */
fun <T : Number> T.percentageOf(total: Number) = if (total == 0) 0 else this.toInt() * 100 / total.toInt()