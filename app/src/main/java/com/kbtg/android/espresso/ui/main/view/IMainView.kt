package com.kbtg.android.espresso.ui.main.view

import android.content.Intent

interface IMainView {

    fun onUpdateListView(listData: ArrayList<String>)

    fun onShowNextPage(intent: Intent)

    fun goToPage3Demo()
}