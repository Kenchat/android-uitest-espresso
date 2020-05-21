package com.kbtg.android.espresso.ui.main.presenter

import android.content.Context
import android.content.Intent
import com.kbtg.android.espresso.ui.main.view.MainControllerView
import com.kbtg.android.espresso.ui.page2.view.DATA
import com.kbtg.android.espresso.ui.page2.view.Page2Activity

class MainPresenterImpl(_view: MainControllerView) : MainPresenter {

    private var view: MainControllerView = _view

    override fun initView() {
        var mListData = ArrayList<String>()
        for (i in 1..5) {
            mListData.add(i.toString())
        }
        view.onUpdateListView(mListData)
    }

    override fun onItemClicked(context: Context, data: String, position: Int) {
        if (position == 0) {
            view.goToPage3Demo()
            return
        }

        val intent = Intent(context, Page2Activity::class.java)
        intent.putExtra(DATA, data)
        view.onShowNextPage(intent)
    }
}