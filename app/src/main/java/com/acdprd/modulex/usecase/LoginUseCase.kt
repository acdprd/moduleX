package com.acdprd.modulex.usecase

import android.util.Log
import com.acdprd.baseproject.utils.extensions.tag
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginUseCase(private var onlyFirstSubscription: Boolean) : /*BaseUseCase<Single<Int>>,*/
    SingleSource<Int> {
    private var wrapper = CountWrapper()
    private var currentSubscription: Disposable? = null
    private val singleShare =
        Single.defer { Single.just(wrapper.get()) }.subscribeOn(Schedulers.io())
            .map { t ->
                Thread.sleep(2000.toLong())
                t
            }
            .observeOn(AndroidSchedulers.mainThread())
    private val obsShare =
        Observable.defer { Observable.just(wrapper.get()) }.subscribeOn(Schedulers.io())
            .map { t ->
                Thread.sleep(2000.toLong())
                t
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                log("obsShare on sub |  | ${wrapper.count}")
            }
            .doFinally {
                log("obsShare finally |  | ${wrapper.count}")
//            wrapper.get()
            }
            .doOnError {
                log("obsShare error |  | ${wrapper.count}")
            }

    //            .share()
    fun subscribe2(observer: SingleObserver<in Int>) {
        log(1)
        if (onlyFirstSubscription) {
            log(2)
            if (currentSubscription == null || currentSubscription?.isDisposed == true) {
                log(3)
                singleShare
                    .doOnSubscribe {
                        this.currentSubscription = it
                    }
                    .doFinally {
                        this.currentSubscription?.dispose()
                    }
                    .subscribe(observer)
            } else {
                log(4)
                Single.never<Int>().subscribe(observer)
            }
        } else {
            log(5)
            currentSubscription?.dispose()
            singleShare
                .doOnSubscribe {
                    this.currentSubscription = it
                }
                .doFinally {
                    this.currentSubscription?.dispose()
                }
                .subscribe(observer)
        }
    }

    override fun subscribe(observer: SingleObserver<in Int>) {
        log(1)
        if (onlyFirstSubscription) {
            log(2)
            if (currentSubscription == null || currentSubscription?.isDisposed == true) {
                log(3)
                obsShare
                    .singleOrError()
                    .doOnSubscribe {
                        this.currentSubscription = it
                    }
                    .doFinally {
                        this.currentSubscription?.dispose()
                    }
                    .subscribe(observer)
            } else {
                log(4)
                Single.never<Int>().subscribe(observer)
            }
        } else {
            log(5)
            currentSubscription?.dispose()
            obsShare
                .singleOrError()
                .doOnSubscribe {
                    this.currentSubscription = it
                }
                .doFinally {
                    this.currentSubscription?.dispose()
                }
                .subscribe(observer)
        }
    }

    private class CountWrapper {
        var count: Int = 0

        fun get(): Int {
            ++count
            return count
        }
    }

    companion object {
        private const val INNER_LOG = true

        @JvmStatic
        fun log(what: Any) {
            if (INNER_LOG) {
                Log.w(tag(), what.toString())
            }
        }
    }
}

fun <T> Single<T>.subscribeIfNoMoreSubscriptions(
    observer: SingleObserver<in T>,
    prevSubscription: Disposable?
) {
    if (prevSubscription == null || prevSubscription.isDisposed) {
        var cd: Disposable? = null
        this
            .doOnSubscribe {
                cd = it
            }
            .doFinally {
                cd?.dispose()
            }
            .subscribe(observer)
    } else {
        Single.never<T>().subscribe(observer)
    }
}