package com.kbtg.android.espresso.ui.countrydetail.presenter

import com.kbtg.android.espresso.ui.countrydetail.model.CountryDetailResponseItem
import com.kbtg.android.espresso.ui.countrydetail.view.ICountryDetailView
import com.kbtg.android.espresso.listcountry.presenter.CountryDetailPresenter
import com.kbtg.android.espresso.network.CovidService
import com.kbtg.android.espresso.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryDetailPresenterImpl(_view: ICountryDetailView) : CountryDetailPresenter {

    private var view: ICountryDetailView = _view

    override fun getCountryDate(countryName: String) {

        val request = ServiceBuilder.buildService(CovidService::class.java)
        val call = request.getCountryDetailData(countryName)

        call.enqueue(object : Callback<ArrayList<CountryDetailResponseItem>> {
            override fun onResponse(call: Call<ArrayList<CountryDetailResponseItem>>, response: Response<ArrayList<CountryDetailResponseItem>>) {
                if (response.isSuccessful) {
                    response.body()?.take(10)?.let { view.updateCountryDetailData(it) }
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryDetailResponseItem>>, t: Throwable) {
                view.onGetDataFailure()
            }
        })
    }
}

