package com.aakash.rxasync

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTimer()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun setTimer() {
        rxTimer(1000, 5000, onRepeat = {
            tvText.text = "$it"
        }, onComplete = {
            tvText2.text = "Complete"
        }).addTo(compositeDisposable)
    }

    private fun setDelayedOperation() {
        val disposable = Observable.just(1)
            .setDelayedCallback(2000, {
                tvText.text = "Hi I am Aakash"
            })
        compositeDisposable.add(disposable)
    }

    private fun setAsyncOperation() {
        val observable1 = Observable.fromCallable {
            return@fromCallable ""
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                tvText.text = "Hi I am Aakash 1"
            }
        compositeDisposable.add(observable1)
    }

}
