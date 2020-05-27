package com.kbtg.android.espresso.ui.nationlist.presenter

import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.ui.base.BasePresenter
import com.kbtg.android.espresso.ui.nationlist.view.INationListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NationListPresenterImpl @Inject constructor(private var nationView: INationListView) :
    BasePresenter<INationListView>(nationView), INationListPresenter {
    @Inject
    lateinit var networkApi: CovidService

    override fun getNationListData() {
        val summaryData = networkApi.getSummaryData()
        addDisposable(summaryData.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    nationView.updateDataSummary(response.Countries.take(10))
                },
                {
                    nationView.onGetDataFailure()
                }
            ))
    }

    override fun onItemSelected(countryName: String) {
        nationView.goToCountryDetail(countryName)
    }
}