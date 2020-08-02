package com.acdprd.filegsonrepository

import io.reactivex.Completable

interface DataSaver {

    fun <T : Any?> saveData(key: String, value: T): Completable
}