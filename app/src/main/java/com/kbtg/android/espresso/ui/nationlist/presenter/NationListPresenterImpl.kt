package com.kbtg.android.espresso.ui.nationlist.presenter

import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.ui.base.BasePresenter
import com.kbtg.android.espresso.ui.nationlist.view.INationListBaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NationListPresenterImpl @Inject constructor(private var nationView: INationListBaseView) :
    BasePresenter<INationListBaseView>(nationView), INationListPresenter {
    @Inject
    lateinit var networkApi: CovidService

    override fun getNationListData() {
        val summaryData = networkApi.getSummaryData()
        addDisposable(summaryData.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    nationView.updateDataSummary(response.Countries)
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