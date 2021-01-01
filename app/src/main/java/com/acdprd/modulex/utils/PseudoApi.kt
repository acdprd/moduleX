package com.acdprd.modulex.utils

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class PseudoApi {

    companion object {
        private const val DELAY = 2000
        private const val SECOND = 1000
        const val RANDOM_NETWORK_ERROR = "random network error"

        fun <T : Any> request(item: T, delay: Long): Single<T> {
            return Single.just(item)
                .subscribeOn(Schedulers.computation())
                .map { t ->
                    Thread.sleep(delay * SECOND)
                    t
                }
        }

        fun <T : Any> request(item: T): Single<T> {
            return Single.just(item).subscribeOn(Schedulers.io())
                .map { t ->
                    Thread.sleep(DELAY.toLong())
                    t
                }
        }

        fun <T : Any> requestObs(item: T): Observable<T> {
            return Observable.defer { Observable.just(item) }.subscribeOn(Schedulers.io())
                .map { t ->
                    Thread.sleep(DELAY.toLong())
                    t
                }
        }

        fun <T : Any> requestWithoutDelay(item: T): Single<T> {
            return Single.just(item).subscribeOn(Schedulers.computation())
        }

        fun request(): Single<Boolean> {
            return Single.just(true).subscribeOn(Schedulers.io())
                .map {
                    Thread.sleep(DELAY.toLong())
                    it
                }
        }

        fun requestWithoutDelay(): Single<Boolean> {
            return Single.just(true).subscribeOn(Schedulers.computation())
        }

        fun <T : Any> requestRandomError(item: T, delay: Long): Single<T> {
            return Single.just(item)
                .subscribeOn(Schedulers.io())
                .map {
                    Thread.sleep(delay * SECOND)
                    if (Random().nextBoolean()) {
                        throw RuntimeException(RANDOM_NETWORK_ERROR)
                    }
                    item
                }
        }

        fun <T : Any> requestRandomError(item: T): Single<T> {
            return Single.just(item)
                .subscribeOn(Schedulers.io())
                .map {
                    Thread.sleep(DELAY.toLong())
                    if (Random().nextBoolean()) {
                        throw RuntimeException(RANDOM_NETWORK_ERROR)
                    }
                    item
                }
        }
    }


}