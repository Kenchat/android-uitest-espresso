package com.kbtg.android.espresso.countrydetail.dispatcher

import com.kbtg.android.espresso.base.DispatcherUtil
import com.kbtg.android.espresso.base.PathJsonConstants
import com.kbtg.android.espresso.network.ApiConstants.API_DAYONE_COUNTRY
import com.kbtg.android.espresso.network.ApiConstants.API_SUMMARY
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class CountryDetailActivityDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            API_SUMMARY -> {
                val mock = DispatcherUtil.getMockResponse(PathJsonConstants.API_SUMMARY)
                mock
            }
            API_DAYONE_COUNTRY -> {
                DispatcherUtil.getMockResponse(PathJsonConstants.API_DAYONE_COUNTRY)
            }
            else -> {
                DispatcherUtil.getEmptyResponse()
            }
        }
    }
}