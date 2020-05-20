package com.kbtg.android.espresso.main.presenter

import android.content.Context
import android.content.Intent
import com.kbtg.android.espresso.main.view.MainControllerView
import com.kbtg.android.espresso.page2.view.DATA
import com.kbtg.android.espresso.page2.view.Page2Activity

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
        val intent = Intent(context, Page2Activity::class.java)
        intent.putExtra(DATA, data)
        view.onShowNextPage(intent)
    }
}