package com.kbtg.android.espresso.ui.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePreseneter<V>(@Volatile var view: V?) {
    companion object {
        var compositeDisposables: CompositeDisposable = CompositeDisposable()
    }

    init {

    }

    protected fun view(): V? {
        return view
    }

    @CallSuper
    fun unbindView() {
        if (compositeDisposables != null) {
            compositeDisposables.clear()
            compositeDisposables.dispose()
        }
        this.view = null
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposables.add(disposable)
    }
}