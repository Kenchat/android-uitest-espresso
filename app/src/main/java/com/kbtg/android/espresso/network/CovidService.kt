package com.kbtg.android.espresso.network

import com.kbtg.android.espresso.network.ApiConstants.API_DAYONE_COUNTRY
import com.kbtg.android.espresso.network.ApiConstants.API_SUMMARY
import com.kbtg.android.espresso.ui.nationdetail.model.NationDetailResponseItem
import com.kbtg.android.espresso.ui.nationlist.model.SummaryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidService {

    @GET(API_SUMMARY)
    fun getSummaryData(): Observable<SummaryResponse>

    @GET(API_DAYONE_COUNTRY)
    fun getCountryDetailData(@Path("country") countryName: String): Observable<ArrayList<NationDetailResponseItem>>

}