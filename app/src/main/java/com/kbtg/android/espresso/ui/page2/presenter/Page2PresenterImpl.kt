package com.kbtg.android.espresso.ui.page2.presenter

import com.kbtg.android.espresso.ui.base.BasePresenter
import com.kbtg.android.espresso.ui.page2.view.IPage2View
import javax.inject.Inject

class Page2PresenterImpl @Inject constructor(page2View: IPage2View) :
    BasePresenter<IPage2View>(page2View), IPage2Presenter {

}