package com.kbtg.android.espresso.page2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.page2.presenter.Page2PresenterImpl
import kotlinx.android.synthetic.main.page2_activity.*

val DATA = "DATA_PARSING"

class Page2Activity : AppCompatActivity(), IPage2View {

    private lateinit var presenter: Page2PresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page2_activity)

        presenter = Page2PresenterImpl(this)

        tvDatas.text = intent.getStringExtra(DATA)
    }
}
