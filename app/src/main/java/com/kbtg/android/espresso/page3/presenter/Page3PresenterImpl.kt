package com.kbtg.android.espresso.page3.presenter

import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.network.ServiceBuilder
import com.kbtg.android.espresso.page3.model.SummaryResponse
import com.kbtg.android.espresso.page3.view.IPage3View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Page3PresenterImpl(_view: IPage3View) : IPage3Presenter {

    private var view: IPage3View = _view

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

