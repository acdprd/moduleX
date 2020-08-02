package com.acdprd.filegsonrepository


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.lang.reflect.Type

class FileLocalStore(storageDir: File) : BaseLocalStore(storageDir), LocalStore {
    override fun getDirectoryName(): String = LOCAL_CACHE_DIRECTORY

    override fun <T : Any> getData(key: String, type: Type): Single<T> {
        return Single.create<T> { emitter ->
            try {
                getWorker(key).also { converter ->
                    val json = converter.get()
                    if (json.isNullOrEmpty()) {
                        emitter.onError(NullPointerException("json in null or empty"))
                    } else {
                        val data = Gson().fromJson<T>(json, type)
                        if (data == null) {
                            emitter.onError(Throwable("cannot deserialize from cache"))
                        } else {
                            emitter.onSuccess(data)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.onError(e)
            }

        }.doOnError{ throwable ->
            throwable.printStackTrace()
        }.subscribeOn(Schedulers.io())
    }

    companion object {
        const val LOCAL_CACHE_DIRECTORY = "localCache"
    }
}