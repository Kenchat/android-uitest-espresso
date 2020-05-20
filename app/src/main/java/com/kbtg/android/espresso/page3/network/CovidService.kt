package com.kbtg.android.espresso.page3.network

import com.kbtg.android.espresso.page3.model.SummaryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CovidService {

    @GET("summary")
    fun getSummaryData(): Call<SummaryResponse>

}