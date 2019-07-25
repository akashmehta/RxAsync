package com.aakash.rxasync

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.uiObservable() : Observable<T> = this.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.setDelayedCallback(
    time: Long,
    onSubscribe: () -> Unit, timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Disposable = this.delay(time, timeUnit)
    .uiObservable()
    .subscribe { onSubscribe() }