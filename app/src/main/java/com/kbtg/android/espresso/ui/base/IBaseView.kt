package com.kbtg.android.espresso.ui.base

interface IBaseView {

    fun loadError(e: Throwable)

    fun loadError(msg: String)


}