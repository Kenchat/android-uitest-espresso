package com.kbtg.android.espresso.ui.main.presenter

import android.content.Context
import android.content.Intent
import com.kbtg.android.espresso.ui.base.BasePreseneter
import com.kbtg.android.espresso.ui.main.view.IMainView
import com.kbtg.android.espresso.ui.page2.view.DATA
import com.kbtg.android.espresso.ui.page2.view.Page2Activity
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(var _view: IMainView) : BasePreseneter<IMainView>(_view), MainPresenter {

    override fun initView() {
        var mListData = ArrayList<String>()
        for (i in 1..5) {
            mListData.add(i.toString())
        }
        _view.onUpdateListView(mListData)
    }

    override fun onItemClicked(context: Context, data: String, position: Int) {
        if (position == 0) {
            _view.goToPage3Demo()
            return
        }

        val intent = Intent(context, Page2Activity::class.java)
        intent.putExtra(DATA, data)
        _view.onShowNextPage(intent)
    }
}