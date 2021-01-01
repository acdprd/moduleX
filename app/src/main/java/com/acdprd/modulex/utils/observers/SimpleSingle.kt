package com.acdprd.modulex.utils.observers


class SimpleSingle<T> @JvmOverloads constructor(onSuccess: (T) -> Unit = {}) :
    LambdaSingle<T>(onSuccess)