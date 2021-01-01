package com.acdprd.modulex.utils.observers

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class LambdaObserver<T> @JvmOverloads constructor(
    var onNext: ((T) -> Unit)? = null,
    var onError: ((Throwable) -> Unit)? = null,
    var onComplete: (() -> Unit)? = null,
    var onSubscribe: ((Disposable) -> Unit)? = null
) : Observer<T> {

    override fun onComplete() {
        onComplete?.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe?.invoke(d)
    }

    override fun onNext(t: T) {
        onNext?.invoke(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        onError?.invoke(e)
    }
}