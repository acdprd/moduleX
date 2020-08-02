package com.acdprd.filegsonrepository

import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

import java.io.File

abstract class BaseLocalStore(directory: File) : DataSaver {
    private val localDirectory: File

    init {
        localDirectory = File(directory, getDirectoryName())
        if (!localDirectory.exists()) {
            localDirectory.mkdirs()
        }
    }

    abstract fun getDirectoryName(): String

    override fun <T : Any?> saveData(key: String, value: T): Completable {
        return Completable.fromCallable {
            getWorker(key).also { converter ->
                value?.let {
                    converter.save(Gson().toJson(value))
                } ?: let {
                    converter.save(null)
                }
            }
        }.subscribeOn(Schedulers.io())
    }

    protected open fun getWorker(key: String): IOWorker {
        return IOWorker(localDirectory, key + FILE_EXTENSION)
    }

    companion object {
        const val FILE_EXTENSION = ".json"
    }
}