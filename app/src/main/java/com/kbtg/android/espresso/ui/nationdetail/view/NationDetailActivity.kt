package com.kbtg.android.espresso.ui.nationdetail.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.base.BaseActivity
import com.kbtg.android.espresso.ui.nationdetail.adapter.NationDetailAdapter
import com.kbtg.android.espresso.ui.nationdetail.model.NationDetailResponseItem
import com.kbtg.android.espresso.ui.nationdetail.presenter.NationDetailPresenterImpl
import kotlinx.android.synthetic.main.country_detail_activity.*
import kotlinx.android.synthetic.main.nations_activity.loading
import javax.inject.Inject

const val COUNTRY_NAME = "COUNTRY_NAME"

class NationDetailActivity : BaseActivity(), INationDetailView {

    @Inject
    lateinit var presenter: NationDetailPresenterImpl

    private var listData = ArrayList<NationDetailResponseItem>()

    override fun setLayout(): Int {
        return R.layout.country_detail_activity
    }

    override fun init(savedInstanceState: Bundle?) {
        rcvCountryDetailByDate.apply {
            layoutManager = LinearLayoutManager(this@NationDetailActivity)
            adapter = NationDetailAdapter(listData)
        }
        loading.visibility = View.VISIBLE
        presenter.getCountryDate(intent.getStringExtra(COUNTRY_NAME) ?: "")
    }

    override fun stopScreen() {
        presenter.unbindView()
    }

    override fun updateCountryDetailData(dataList: List<NationDetailResponseItem>) {
        loading.visibility = View.GONE

        tvCountryName.text = intent.getStringExtra(COUNTRY_NAME)
        listData.clear()
        listData.addAll(dataList)
        rcvCountryDetailByDate.adapter?.run { notifyDataSetChanged() }
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
