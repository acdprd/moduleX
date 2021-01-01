package com.acdprd.modulex.utils.observers

class SimpleObserver<T>(onNext: (T) -> Unit) : LambdaObserver<T>(onNext)