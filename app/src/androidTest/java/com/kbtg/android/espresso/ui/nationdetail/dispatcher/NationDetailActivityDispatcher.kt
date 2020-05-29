package com.kbtg.android.espresso.ui.nationdetail.dispatcher

import android.util.Log
import com.kbtg.android.espresso.base.DispatcherUtil
import com.kbtg.android.espresso.base.PathJsonConstants
import com.kbtg.android.espresso.network.ApiConstants.API_DAYONE_COUNTRY_THAILAND
import com.kbtg.android.espresso.network.ApiConstants.API_SUMMARY
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class NationDetailActivityDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        Log.d(
            "LOG",
            "CountryDetailActivityDispatcher | url: ${request.requestUrl} | path: ${request.path}"
        )
        return when (request.path) {
            API_SUMMARY -> {
                val mock = DispatcherUtil.getMockResponse(PathJsonConstants.JSON_SUMMARY)
                Log.d("LOG", "\t\t\t\t | ${PathJsonConstants.JSON_SUMMARY} | mock: $mock")
                mock
            }
            API_DAYONE_COUNTRY_THAILAND -> {
                val mock =
                    DispatcherUtil.getMockResponse(PathJsonConstants.JSON_DAYONE_COUNTRY_THAILAND)
                Log.d(
                    "LOG",
                    "\t\t\t\t | ${PathJsonConstants.JSON_DAYONE_COUNTRY_THAILAND} | mock: $mock"
                )
                mock
            }
            else -> {
                Log.d("LOG", "\t\t\t\t | empty response")
                DispatcherUtil.getEmptyResponse()
            }
        }
    }
}