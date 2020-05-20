package com.kbtg.android.espresso.page3.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
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

    private var summaryDataAdapter: SummaryDataAdapter? = null
    private var listData = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page3_activity)

        presenter = Page3PresenterImpl(this)

        rcvCovidSummaryData.apply {
            layoutManager = LinearLayoutManager(this@Page3Activity)
            summaryDataAdapter = SummaryDataAdapter(listData)
            adapter = summaryDataAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        loading.visibility = View.VISIBLE
    }

    override fun updateDataSummary(dataList: List<Country>) {
        loading.visibility = View.GONE
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)
        tvTime.text = "Time: $formatedDate"

        listData.clear()
        listData.addAll(dataList)
        summaryDataAdapter?.setItemList(listData)

    }

    override fun onGetDataFailure() {
        loading.visibility = View.GONE
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Get data in failure")

        builder.setPositiveButton(android.R.string.yes) { dialog, which -> Unit }

        builder.show()
    }
}
