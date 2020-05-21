package com.kbtg.android.espresso.ui.countrydetail.presenter

import com.kbtg.android.espresso.listcountry.presenter.NationDetailPresenter
import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.ui.base.BasePreseneter
import com.kbtg.android.espresso.ui.countrydetail.view.INationDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NationDetailPresenterImpl @Inject constructor(var _view: INationDetailView) : NationDetailPresenter, BasePreseneter<INationDetailView>(_view) {

    @Inject
    lateinit var mNetworkApi: CovidService

    override fun getCountryDate(countryName: String) {

        val data = mNetworkApi.getCountryDetailData(countryName)

        addDisposable(data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnDispose(Action { _view.onGetDataFailure() })
                .subscribe(
                        { response ->
                            _view.updateCountryDetailData(response)
                        },
                        { error ->
                            _view.onGetDataFailure()
                        }
                ))
    }
}