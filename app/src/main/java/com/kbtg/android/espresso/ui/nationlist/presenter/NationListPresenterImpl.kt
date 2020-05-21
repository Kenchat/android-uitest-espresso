package com.kbtg.android.espresso.ui.nationlist.presenter

import android.util.Log
import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.ui.base.BasePreseneter
import com.kbtg.android.espresso.ui.nationlist.view.INationListBaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NationListPresenterImpl @Inject constructor(var _view: INationListBaseView) : BasePreseneter<INationListBaseView>(_view), INationListPresenter {

    @Inject
    lateinit var mNetworkApi: CovidService

    init {

    }

    override fun getNationListData() {
        val summaryData = mNetworkApi.getSummaryData()
        addDisposable(summaryData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnDispose(Action { _view.onGetDataFailure() })
                .subscribe(
                        { response ->
                            _view.updateDataSummary(response.Countries)
                        },
                        { error ->
                            _view.onGetDataFailure()
                        }
                ))
    }

    override fun onItemSelected(countryName: String) {
        _view.goToCountryDetail(countryName)
    }
}