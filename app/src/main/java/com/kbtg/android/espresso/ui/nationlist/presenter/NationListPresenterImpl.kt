package com.kbtg.android.espresso.ui.nationlist.presenter

import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.network.ServiceBuilder
import com.kbtg.android.espresso.ui.nationlist.model.SummaryResponse
import com.kbtg.android.espresso.ui.nationlist.view.INationListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NationListPresenterImpl(_view: INationListView) : INationListPresenter {

    private var view: INationListView = _view

    init {
        val request = ServiceBuilder.buildService(CovidService::class.java)
        val call = request.getSummaryData()

        call.enqueue(object : Callback<SummaryResponse> {
            override fun onResponse(call: Call<SummaryResponse>, response: Response<SummaryResponse>) {
                if (response.isSuccessful) {
                    response.body()?.Countries?.let { view.updateDataSummary(it) }
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                view.onGetDataFailure()
            }
        })
    }

    override fun onItemSelected(countryName: String) {
        view.goToCountryDetail(countryName)
    }

}

