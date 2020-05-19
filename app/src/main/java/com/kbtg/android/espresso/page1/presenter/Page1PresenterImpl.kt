package com.kbtg.android.espresso.page1.presenter

import android.content.Context
import android.content.Intent
import com.kbtg.android.espresso.page2.view.DATA
import com.kbtg.android.espresso.page2.view.Page2Activity
import com.kbtg.android.espresso.page1.view.IPage1View

class Page1PresenterImpl(_view: IPage1View) : IPage1Presenter {

    private var view: IPage1View = _view

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