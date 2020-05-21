package com.kbtg.android.espresso.ui.countrydetail.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.countrydetail.adapter.CountryDetailAdapter
import com.kbtg.android.espresso.ui.countrydetail.model.CountryDetailResponseItem
import com.kbtg.android.espresso.ui.countrydetail.presenter.CountryDetailPresenterImpl
import kotlinx.android.synthetic.main.country_detail_activity.*
import kotlinx.android.synthetic.main.page3_activity.loading

val COUNTRY_NAME = "COUNTRY_NAME"

class CountryDetailActivity : AppCompatActivity(), ICountryDetailView {

    private lateinit var presenter: CountryDetailPresenterImpl

    private var summaryDataAdapter: CountryDetailAdapter? = null
    private var listData = ArrayList<CountryDetailResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_detail_activity)

        presenter = CountryDetailPresenterImpl(this)

        rcvCountryDetailByDate.apply {
            layoutManager = LinearLayoutManager(this@CountryDetailActivity)
            summaryDataAdapter = CountryDetailAdapter(listData)
            adapter = summaryDataAdapter
        }
        loading.visibility = View.VISIBLE
        presenter.getCountryDate(intent.getStringExtra(COUNTRY_NAME))
    }

    override fun updateCountryDetailData(dataList: List<CountryDetailResponseItem>) {
        loading.visibility = View.GONE

        tvCountryName.text = intent.getStringExtra(COUNTRY_NAME)
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
}
