package com.aakash.rxasync

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.uiObservable() : Observable<T> = this.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.setDelayedCallback(
    time: Long,
    onSubscribe: () -> Unit, timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Disposable = this.delay(time, timeUnit)
    .uiObservable()
    .subscribe { onSubscribe() }


fun rxTimer(timeInterval: Long, duration: Long, onRepeat: (time: Long) -> Unit = {}, onComplete: () -> Unit = {}): Disposable =
    Observable.interval(0, timeInterval, TimeUnit.MILLISECONDS)
        .take(duration, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext {
            onRepeat(it)
        }.doOnComplete {
            onComplete()
        }.subscribe()
