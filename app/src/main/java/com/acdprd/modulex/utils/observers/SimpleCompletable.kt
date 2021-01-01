package com.acdprd.modulex.utils.observers


class SimpleCompletable @JvmOverloads constructor(onComplete: () -> Unit = {}) :
    LambdaCompletable(onComplete)