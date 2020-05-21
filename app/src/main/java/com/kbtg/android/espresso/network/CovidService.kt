package com.kbtg.android.espresso.network

import com.kbtg.android.espresso.ui.countrydetail.model.CountryDetailResponseItem
import com.kbtg.android.espresso.ui.nationlist.model.SummaryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidService {

    @GET("summary")
    fun getSummaryData(): Observable<SummaryResponse>

    @GET("dayone/country/{country}")
    fun getCountryDetailData(@Path("country") countryName: String): Observable<ArrayList<CountryDetailResponseItem>>

}