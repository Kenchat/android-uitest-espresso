package com.kbtg.android.espresso.ui.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V>(@Volatile var view: V?) {
    //companion object {
        var compositeDisposables: CompositeDisposable = CompositeDisposable()
    //}

    @CallSuper
    fun unbindView() {
        compositeDisposables.dispose()
        compositeDisposables.clear()
        this.view = null
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposables.add(disposable)
    }
}