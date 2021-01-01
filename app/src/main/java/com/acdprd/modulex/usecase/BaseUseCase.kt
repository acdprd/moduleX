package com.acdprd.modulex.usecase

interface BaseUseCase<R : Any> {
    fun invoke(): R
}