package com.kbtg.android.espresso.countrydetail.dispatcher

import android.util.Log
import com.kbtg.android.espresso.base.DispatcherUtil
import com.kbtg.android.espresso.base.PathJsonConstants
import com.kbtg.android.espresso.network.ApiConstants.API_DAYONE_COUNTRY_THAI
import com.kbtg.android.espresso.network.ApiConstants.API_SUMMARY
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class CountryDetailActivityDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        Log.d(
            "LOG",
            "CountryDetailActivityDispatcher | url: ${request.requestUrl} | path: ${request.path}"
        )
        return when (request.path) {
            API_SUMMARY -> {
                val mock = DispatcherUtil.getMockResponse(PathJsonConstants.API_SUMMARY)
                Log.d("LOG", "\t\t\t\t | ${PathJsonConstants.API_SUMMARY} | mock: ${mock}")
                mock
            }
            API_DAYONE_COUNTRY_THAI -> {
                val mock = DispatcherUtil.getMockResponse(PathJsonConstants.API_DAYONE_COUNTRY_THAILAND)
                Log.d("LOG", "\t\t\t\t | ${PathJsonConstants.API_DAYONE_COUNTRY_THAILAND} | mock: ${mock}")
                mock
            }
            else -> {
                Log.d("LOG", "\t\t\t\t | empty response")
                DispatcherUtil.getEmptyResponse()
            }
        }
    }
}