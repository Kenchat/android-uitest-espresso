package com.kbtg.android.espresso.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.main.adapter.ListHomeAdapter
import com.kbtg.android.espresso.main.presenter.MainPresenterImpl
import com.kbtg.android.espresso.page3.view.Page3Activity
import kotlinx.android.synthetic.main.page1_activity.*

class MainActivity : AppCompatActivity(), MainControllerView {

    private var mAdapter: ListHomeAdapter? = null
    private var mListData = ArrayList<String>()

    private lateinit var presenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1_activity)

        presenter = MainPresenterImpl(this)

        presenter.initView()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter =
                    ListHomeAdapter(this@MainActivity,
                            mListData,
                            onItemClick = { item, position ->
                                presenter.onItemClicked(
                                        this@MainActivity,
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

    override fun goToPage3Demo() {
        val intent = Intent(this, Page3Activity::class.java)
        startActivity(intent)
    }
}