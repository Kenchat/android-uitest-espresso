package com.kbtg.android.espresso.main.presenter

import android.content.Context

interface MainPresenter {

    fun initView()

    fun onItemClicked(context: Context, data: String, position: Int)
}