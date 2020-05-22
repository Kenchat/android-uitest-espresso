package com.kbtg.android.espresso.ui.main.view

import android.content.Intent
import com.kbtg.android.espresso.ui.base.IBaseView

interface IMainView : IBaseView {

    fun onUpdateListView(listData: ArrayList<String>)

    fun onShowNextPage(intent: Intent)

    fun goToPage3Demo()
}