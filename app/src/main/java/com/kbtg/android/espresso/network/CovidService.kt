package com.kbtg.android.espresso.network

import com.kbtg.android.espresso.countrydetail.model.CountryDetailResponseItem
import com.kbtg.android.espresso.network.ApiConstants.API_DAYONE_COUNTRY
import com.kbtg.android.espresso.network.ApiConstants.API_SUMMARY
import com.kbtg.android.espresso.page3.model.SummaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidService {

    @GET(API_SUMMARY)
    fun getSummaryData(): Call<SummaryResponse>

    @GET(API_DAYONE_COUNTRY)
    fun getCountryDetailData(@Path("country") countryName: String): Call<ArrayList<CountryDetailResponseItem>>

}