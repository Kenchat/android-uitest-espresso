package com.kbtg.android.espresso.ui.nationlist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.base.BaseActivity
import com.kbtg.android.espresso.ui.countrydetail.view.COUNTRY_NAME
import com.kbtg.android.espresso.ui.countrydetail.view.NationDetailActivity
import com.kbtg.android.espresso.ui.nationlist.adapter.NationListDataAdapter
import com.kbtg.android.espresso.ui.nationlist.model.Country
import com.kbtg.android.espresso.ui.nationlist.presenter.NationListPresenterImpl
import kotlinx.android.synthetic.main.nations_activity.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NationListActivity : BaseActivity(), INationListBaseView {

    @Inject
    lateinit var presenter: NationListPresenterImpl

    private var nationListDataAdapter: NationListDataAdapter? = null
    private var listData = ArrayList<Country>()

    override fun setLayout(): Int {
        return R.layout.nations_activity
    }

    override fun init(savedInstanceState: Bundle?) {
        loading.visibility = View.VISIBLE


        rcvCovidSummaryData.apply {
            layoutManager = LinearLayoutManager(this@NationListActivity)
            nationListDataAdapter = NationListDataAdapter(listData, onItemClick = { item ->
                presenter.onItemSelected(item)
            })
            adapter = nationListDataAdapter
        }
        presenter.getNationListData()
    }

    override fun onStartScreen() {

    }

    override fun stopScreen() {
        presenter.unbindView()
    }

    override fun updateDataSummary(dataList: List<Country>) {
        loading.visibility = View.GONE
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)
        tvTime.text = "Time: $formatedDate"

        listData.clear()
        listData.addAll(dataList)
        nationListDataAdapter?.setItemList(listData)

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
