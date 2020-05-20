package com.kbtg.android.espresso.page1.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.page1.adapter.ListHomeAdapter
import com.kbtg.android.espresso.page1.presenter.Page1PresenterImpl
import kotlinx.android.synthetic.main.page1_activity.*

class Page1Activity : AppCompatActivity(), IPage1View {

    private var mAdapter: ListHomeAdapter? = null
    private var mListData = ArrayList<String>()

    private lateinit var presenter: Page1PresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1_activity)

        presenter = Page1PresenterImpl(this)

        presenter.initView()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Page1Activity)
            mAdapter =
                    ListHomeAdapter(this@Page1Activity,
                            mListData,
                            onItemClick = { item, position ->
                                presenter.onItemClicked(
                                        this@Page1Activity,
                                        item,
                                        position
                                )
                            })

            adapter = mAdapter
        }
    }

    override fun onUpdateListView(listData: ArrayList<String>) {
        mListData.clear()
        mListData.addAll(listData)
        mAdapter?.setItemList(mListData)
    }

    override fun onShowNextPage(intent: Intent) {
        startActivity(intent)
    }
}