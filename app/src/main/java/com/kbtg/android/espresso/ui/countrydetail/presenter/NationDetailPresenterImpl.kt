package com.kbtg.android.espresso.ui.countrydetail.presenter

import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.ui.base.BasePresenter
import com.kbtg.android.espresso.ui.countrydetail.view.INationDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NationDetailPresenterImpl @Inject constructor(private var nationDetailView: INationDetailView) :
    NationDetailPresenter, BasePresenter<INationDetailView>(nationDetailView) {

    @Inject
    lateinit var networkApi: CovidService

    override fun getCountryDate(countryName: String) {

        val data = networkApi.getCountryDetailData(countryName)
        addDisposable(data.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    nationDetailView.updateCountryDetailData(response.reversed())
                },
                {
                    nationDetailView.onGetDataFailure()
                }
            ))
    }
}