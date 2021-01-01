package com.acdprd.modulex.utils.observers

import io.reactivex.functions.Consumer


class LambdaConsumer<T : Any> @JvmOverloads constructor(var accept: (T) -> Unit = {}) :
    Consumer<T> {
    override fun accept(t: T) {
        accept.invoke(t)
    }
}