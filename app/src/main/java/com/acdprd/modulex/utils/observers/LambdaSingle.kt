package com.acdprd.modulex.utils.observers

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

open class LambdaSingle<T> @JvmOverloads constructor(
    var onSuccess: (T) -> Unit = {},
    var onError: (Throwable) -> Unit = {},
    var onSubscribe: (Disposable) -> Unit = {}
) : SingleObserver<T> {

    override fun onSuccess(t: T) {
        onSuccess.invoke(t)
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe.invoke(d)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError.invoke(e)
    }
}