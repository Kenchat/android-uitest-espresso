package com.kbtg.android.espresso.ui.main.presenter

import android.content.Context
import android.content.Intent
import com.kbtg.android.espresso.ui.base.BasePresenter
import com.kbtg.android.espresso.ui.main.view.IMainView
import com.kbtg.android.espresso.ui.page2.view.DATA
import com.kbtg.android.espresso.ui.page2.view.Page2Activity
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(private var mainView: IMainView) :
    BasePresenter<IMainView>(mainView), MainPresenter {

    override fun initView() {
        val listData = ArrayList<String>()
        for (i in 1..5) {
            listData.add(i.toString())
        }
        mainView.onUpdateListView(listData)
    }

    override fun onItemClicked(context: Context, data: String, position: Int) {
        if (position == 0) {
            mainView.goToPage3Demo()
            return
        }

        val intent = Intent(context, Page2Activity::class.java)
        intent.putExtra(DATA, data)
        mainView.onShowNextPage(intent)
    }
}