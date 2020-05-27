package com.kbtg.android.espresso.ui.nationlist.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.base.BaseActivity
import com.kbtg.android.espresso.ui.nationdetail.view.COUNTRY_NAME
import com.kbtg.android.espresso.ui.nationdetail.view.NationDetailActivity
import com.kbtg.android.espresso.ui.nationlist.adapter.NationListDataAdapter
import com.kbtg.android.espresso.ui.nationlist.model.Country
import com.kbtg.android.espresso.ui.nationlist.presenter.NationListPresenterImpl
import kotlinx.android.synthetic.main.nations_activity.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NationListActivity : BaseActivity(), INationListView {

    @Inject
    lateinit var presenter: NationListPresenterImpl

    private var listData = ArrayList<Country>()

    override fun setLayout(): Int {
        return R.layout.nations_activity
    }

    override fun init(savedInstanceState: Bundle?) {
        loading.visibility = View.VISIBLE

        rcvCovidSummaryData.apply {
            layoutManager = LinearLayoutManager(this@NationListActivity)
            adapter = NationListDataAdapter(listData, onItemClick = { item ->
                presenter.onItemSelected(item)
            })
        }
        presenter.getNationListData()
    }

    override fun stopScreen() {
        presenter.unbindView()
    }

    @SuppressLint("SetTextI18n")
    override fun updateDataSummary(dataList: List<Country>) {
        loading.visibility = View.GONE

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("E dd-MMM-yyyy hh:mm:ss a")
        val formatted = current.format(formatter)
        tvTime.text = "Time: $formatted"

        listData.clear()
        listData.addAll(dataList)
        rcvCovidSummaryData.adapter?.run { notifyDataSetChanged() }

    }

    override fun onGetDataFailure() {
        loading.visibility = View.GONE
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Get data in failure")

        builder.setPositiveButton(android.R.string.yes) { _, _ -> onBackPressed() }

        builder.show()
    }

    override fun goToCountryDetail(countryName: String) {
        val intent = Intent(this@NationListActivity, NationDetailActivity::class.java)
        intent.putExtra(COUNTRY_NAME, countryName)
        startActivity(intent)
    }
}
