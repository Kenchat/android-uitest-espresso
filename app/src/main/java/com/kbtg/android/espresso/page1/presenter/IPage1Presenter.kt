package com.kbtg.android.espresso.page1.presenter

import android.content.Context

interface IPage1Presenter {

    fun initView()

    fun onItemClicked(context: Context, data: String, position: Int)
}