package com.kbtg.android.espresso.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.base.BaseActivity
import com.kbtg.android.espresso.ui.main.adapter.ListHomeAdapter
import com.kbtg.android.espresso.ui.main.presenter.MainPresenterImpl
import com.kbtg.android.espresso.ui.nationlist.view.NationListActivity
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity : BaseActivity(), IMainView {

    private var adapter: ListHomeAdapter? = null
    private var listData = ArrayList<String>()

    @Inject
    lateinit var presenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = MainPresenterImpl(this)


    }

    override fun setLayout(): Int {
        return R.layout.main_activity
    }

    override fun init(savedInstanceState: Bundle?) {
        presenter.initView()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =
                    ListHomeAdapter(this@MainActivity,
                            listData,
                            onItemClick = { item, position ->
                                presenter.onItemClicked(
                                        this@MainActivity,
                                        item,
                                        position
                                )
                            })
            adapter = adapter
        }
    }

    override fun onStartScreen() {
    }

    override fun stopScreen() {
        presenter.unbindView()
    }

    override fun onUpdateListView(listData: ArrayList<String>) {
        this.listData.clear()
        this.listData.addAll(listData)
        adapter?.setItemList(this.listData)
    }

    override fun onShowNextPage(intent: Intent) {
        startActivity(intent)
    }

    override fun goToPage3Demo() {
        val intent = Intent(this, NationListActivity::class.java)
        startActivity(intent)
    }
}