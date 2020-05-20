package com.kbtg.android.espresso.page3.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.page3.adapter.SummaryDataAdapter
import com.kbtg.android.espresso.page3.model.Country
import com.kbtg.android.espresso.page3.presenter.Page3PresenterImpl
import kotlinx.android.synthetic.main.page3_activity.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Page3Activity : AppCompatActivity(), IPage3View {

    private lateinit var presenter: Page3PresenterImpl

    private var mAdapter: SummaryDataAdapter? = null
    private var mListData = ArrayList<Country>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page3_activity)

        presenter = Page3PresenterImpl(this)

        rcvCovidSummaryData.apply {
            layoutManager = LinearLayoutManager(this@Page3Activity)
            mAdapter = SummaryDataAdapter(mListData)
            adapter = mAdapter
        }
    }

    override fun updateDataSummary(dataList: List<Country>) {
        mListData.clear()
        mListData.addAll(dataList)
        mAdapter?.setItemList(mListData)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)
        tvTime.text = "Time: $formatedDate"
    }
}
