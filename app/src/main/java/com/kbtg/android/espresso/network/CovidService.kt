package com.kbtg.android.espresso.network

import com.kbtg.android.espresso.countrydetail.model.CountryDetailResponseItem
import com.kbtg.android.espresso.page3.model.SummaryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidService {

    @GET("summary")
    fun getSummaryData(): Call<SummaryResponse>

    @GET("dayone/country/{country}")
    fun getCountryDetailData(@Path("country") countryName: String): Call<ArrayList<CountryDetailResponseItem>>

}