package com.kbtg.android.espresso.main.view

import android.content.Intent

interface MainControllerView {

    fun onUpdateListView(listData: ArrayList<String>)

    fun onShowNextPage(intent: Intent)

    fun goToPage3Demo()
}