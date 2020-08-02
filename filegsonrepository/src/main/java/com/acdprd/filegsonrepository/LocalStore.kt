package com.acdprd.filegsonrepository


import io.reactivex.Single
import java.lang.reflect.Type

interface LocalStore  {

    fun <T : Any> getData(key: String, type: Type): Single<T>
}