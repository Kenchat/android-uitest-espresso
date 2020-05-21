package com.kbtg.android.espresso.ui.base

interface IBaseView{

    fun showLoading()

    fun hideLoading()

    fun loadError(e: Throwable)

    fun loadError(msg: String)


}