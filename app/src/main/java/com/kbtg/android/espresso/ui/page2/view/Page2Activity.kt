package com.kbtg.android.espresso.ui.page2.view

import android.os.Bundle
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.base.BaseActivity
import com.kbtg.android.espresso.ui.page2.presenter.Page2PresenterImpl
import kotlinx.android.synthetic.main.page2_activity.*
import javax.inject.Inject

const val DATA = "DATA_PARSING"

class Page2Activity : BaseActivity(), IPage2View {

    @Inject
    lateinit var presenter: Page2PresenterImpl

    override fun setLayout(): Int {
        return R.layout.page2_activity
    }

    override fun init(savedInstanceState: Bundle?) {
        tvDatas.text = intent.getStringExtra(DATA)
    }

    override fun stopScreen() {
        presenter.unbindView()
    }
}
