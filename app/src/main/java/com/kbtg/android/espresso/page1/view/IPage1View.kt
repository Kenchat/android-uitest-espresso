package com.kbtg.android.espresso.page1.view

import android.content.Intent

interface IPage1View {

    fun onUpdateListView(listData: ArrayList<String>)

    fun onShowNextPage(intent: Intent)
}