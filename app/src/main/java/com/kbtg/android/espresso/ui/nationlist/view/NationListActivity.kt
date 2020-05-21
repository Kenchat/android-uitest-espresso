package com.kbtg.android.espresso.ui.nationlist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.countrydetail.view.COUNTRY_NAME
import com.kbtg.android.espresso.ui.countrydetail.view.CountryDetailActivity
import com.kbtg.android.espresso.ui.nationlist.adapter.SummaryDataAdapter
import com.kbtg.android.espresso.ui.nationlist.model.Country
import com.kbtg.android.espresso.ui.nationlist.presenter.NationListPresenterImpl
import kotlinx.android.synthetic.main.page3_activity.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NationListActivity : AppCompatActivity(), INationListView {

    private lateinit var presenter: NationListPresenterImpl

    private var summaryDataAdapter: SummaryDataAdapter? = null
    private var listData = ArrayList<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page3_activity)

        presenter = NationListPresenterImpl(this)

        loading.visibility = View.VISIBLE

        rcvCovidSummaryData.apply {
            layoutManager = LinearLayoutManager(this@NationListActivity)
            summaryDataAdapter = SummaryDataAdapter(listData, onItemClick = { item ->
                presenter.onItemSelected(item)
            })
            adapter = summaryDataAdapter
        }
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

        builder.setPositiveButton(android.R.string.yes) { _, _ -> Unit }

        builder.show()
    }

    override fun goToCountryDetail(countryName: String) {
        val intent = Intent(this@NationListActivity, CountryDetailActivity::class.java)
        intent.putExtra(COUNTRY_NAME, countryName)
        startActivity(intent)
    }
}
